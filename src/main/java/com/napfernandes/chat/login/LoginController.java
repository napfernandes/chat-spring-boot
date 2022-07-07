package com.napfernandes.chat.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.napfernandes.chat.login.dto.LoginInput;
import com.napfernandes.chat.login.dto.LoginOutput;
import com.napfernandes.chat.login.exception.InvalidCredentialsException;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginOutput loginWithCredentials(@RequestBody LoginInput loginInput) throws InvalidCredentialsException {
        return this.loginService.loginWithCredentials(loginInput);
    }
}
