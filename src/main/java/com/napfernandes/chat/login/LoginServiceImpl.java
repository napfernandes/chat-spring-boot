package com.napfernandes.chat.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.crypto.CryptoService;
import com.napfernandes.chat.login.dto.GenerateTokenInput;
import com.napfernandes.chat.login.dto.LoginInput;
import com.napfernandes.chat.login.dto.LoginOutput;
import com.napfernandes.chat.login.exception.InvalidCredentialsException;
import com.napfernandes.chat.service.ValidatorService;
import com.napfernandes.chat.token.TokenService;
import com.napfernandes.chat.user.User;
import com.napfernandes.chat.user.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ValidatorService<LoginInput> loginInputValidator;

    @Override
    public LoginOutput loginWithCredentials(LoginInput input) throws InvalidCredentialsException {
        this.loginInputValidator.validate(input);

        User userWithEmail = this.userRepository.getByEmail(input.getEmail());
        if (userWithEmail == null) {
            throw new InvalidCredentialsException();
        }

        String hashedPassword = this.cryptoService.hashValue(input.getPassword(), userWithEmail.getSalt());
        if (!hashedPassword.equals(userWithEmail.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Map<String, Object> tokenInformation = new HashMap<>();
        tokenInformation.put("email", userWithEmail.getEmail());
        tokenInformation.put("userId", userWithEmail.getId());

        String accessToken = this.tokenService.generateToken(new GenerateTokenInput(
                userWithEmail.getId(),
                userWithEmail.getEmail(),
                tokenInformation));

        return new LoginOutput(accessToken, "");
    }

}
