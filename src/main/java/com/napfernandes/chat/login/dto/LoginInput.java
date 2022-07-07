package com.napfernandes.chat.login.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginInput {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
