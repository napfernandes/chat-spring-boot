package com.napfernandes.chat.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginOutput {
    private String accessToken;
    private String refreshToken;
}
