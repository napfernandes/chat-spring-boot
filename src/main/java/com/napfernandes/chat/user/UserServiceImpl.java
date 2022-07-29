package com.napfernandes.chat.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.crypto.CryptoService;
import com.napfernandes.chat.crypto.exception.RandomValueNumberOfBytesException;
import com.napfernandes.chat.service.ValidatorService;
import com.napfernandes.chat.user.dto.UserInput;
import com.napfernandes.chat.user.dto.UserOutput;
import com.napfernandes.chat.user.exception.UserAlreadyExistsException;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final CryptoService cryptoService;
    private final UserRepository userRepository;
    private final ValidatorService<UserInput> userInputValidator;

    public UserServiceImpl(
            ModelMapper modelMapper,
            CryptoService cryptoService,
            UserRepository userRepository,
            ValidatorService<UserInput> userInputValidator) {
        this.modelMapper = modelMapper;
        this.cryptoService = cryptoService;
        this.userRepository = userRepository;
        this.userInputValidator = userInputValidator;
    }

    @Override
    public List<UserOutput> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserOutput.class))
                .collect(Collectors.toList());
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

    @Override
    public UserOutput getUserById(String userId) {
        var user = userRepository.getById(userId);

        return this.modelMapper.map(user, UserOutput.class);
    }

}
