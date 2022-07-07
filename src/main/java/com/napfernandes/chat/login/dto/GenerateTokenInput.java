package com.napfernandes.chat.login.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenerateTokenInput {

    @NotNull
    private String userId;

    @NotNull
    private String subject;

    private Map<String, Object> claims;

    public GenerateTokenInput() {
        this.claims = new HashMap<String, Object>();
    }

    public GenerateTokenInput(String userId, String subject) {
        this();
        this.userId = userId;
        this.subject = subject;
    }
}
