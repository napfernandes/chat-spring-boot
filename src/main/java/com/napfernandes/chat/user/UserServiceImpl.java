package com.napfernandes.chat.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.cache.CacheService;
import com.napfernandes.chat.crypto.CryptoService;
import com.napfernandes.chat.crypto.exception.RandomValueNumberOfBytesException;
import com.napfernandes.chat.service.ValidatorService;
import com.napfernandes.chat.user.dto.UserInput;
import com.napfernandes.chat.user.dto.UserOutput;
import com.napfernandes.chat.user.exception.UserAlreadyExistsException;
import com.napfernandes.chat.user.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final CacheService cacheService;
    private final CryptoService cryptoService;
    private final UserRepository userRepository;
    private final ValidatorService<UserInput> userInputValidator;

    public UserServiceImpl(
            ModelMapper modelMapper,
            CacheService cacheService,
            CryptoService cryptoService,
            UserRepository userRepository,
            ValidatorService<UserInput> userInputValidator) {
        this.modelMapper = modelMapper;
        this.cacheService = cacheService;
        this.cryptoService = cryptoService;
        this.userRepository = userRepository;
        this.userInputValidator = userInputValidator;
    }

    @Override
    public List<UserOutput> findAllUsers() {
        String cacheKey = "findAllUsers";

        List<UserOutput> users = this.cacheService.getItemAsList(cacheKey, UserOutput.class);
        if (users != null) {
            return users;
        }

        users = this.userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserOutput.class))
                .collect(Collectors.toList());
        this.cacheService.setItem(cacheKey, users);

        return users;
    }

    @Override
    public UserOutput getUserById(String userId) throws UserNotFoundException {
        String cacheKey = String.format("getUserById#%s", userId);
        UserOutput userOutput = this.cacheService.getItem(cacheKey, UserOutput.class);

        if (userOutput != null) {
            return userOutput;
        }

        User user = userRepository.getById(userId);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        userOutput = this.modelMapper.map(user, UserOutput.class);
        this.cacheService.setItem(cacheKey, userOutput);

        return userOutput;
    }

    @Override
    public UserOutput insertUser(UserInput input) throws RandomValueNumberOfBytesException, UserAlreadyExistsException {
        this.userInputValidator.validate(input);

        User existingUser = userRepository.getByEmail(input.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistsException(input.getEmail());
        }

        User user = this.modelMapper.map(input, User.class);
        String salt = this.cryptoService.generateRandomValue(32);
        String hashedPassword = this.cryptoService.hashValue(input.getPassword(), salt);

        user.setSalt(salt);
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.insert(user);

        return this.modelMapper.map(user, UserOutput.class);
    }
}
