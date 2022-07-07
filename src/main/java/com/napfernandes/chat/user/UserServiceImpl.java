package com.napfernandes.chat.user;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.crypto.CryptoService;
import com.napfernandes.chat.crypto.exception.RandomValueNumberOfBytesException;
import com.napfernandes.chat.service.ValidatorService;
import com.napfernandes.chat.user.dto.UserInput;
import com.napfernandes.chat.user.dto.UserOutput;
import com.napfernandes.chat.user.exception.UserAlreadyExistsException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ValidatorService<UserInput> userInputValidator;

    @Autowired
    private ModelMapper modelMapper;

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
        user.setCreatedAt(new Date());

        userRepository.insert(user);

        return this.modelMapper.map(user, UserOutput.class);
    }

}
