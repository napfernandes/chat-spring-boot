package com.napfernandes.chat.token;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.napfernandes.chat.login.dto.GenerateTokenInput;

public class TokenServiceUnitTest {

    private TokenService tokenService;

    @BeforeEach
    public void beforeEach() {
        this.tokenService = new TokenServiceImpl("shhh", 100000);
    }

    @AfterEach
    public void afterEach() {
        this.tokenService = null;
    }

    @Test
    public void generateToken_shouldBuildJwtToken() {
        GenerateTokenInput input = new GenerateTokenInput("user-id", "sub-email");
        assertNotNull(this.tokenService.generateToken(input));
    }
}
