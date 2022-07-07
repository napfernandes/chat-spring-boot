package com.napfernandes.chat.token;

import com.napfernandes.chat.login.dto.GenerateTokenInput;

public interface TokenService {
    String generateToken(GenerateTokenInput input);
}
