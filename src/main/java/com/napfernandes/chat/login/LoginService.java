package com.napfernandes.chat.login;

import com.napfernandes.chat.login.dto.LoginInput;
import com.napfernandes.chat.login.dto.LoginOutput;
import com.napfernandes.chat.login.exception.InvalidCredentialsException;

public interface LoginService {

    LoginOutput loginWithCredentials(LoginInput input) throws InvalidCredentialsException;
}
