package com.napfernandes.chat.login.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

    public GenerateTokenInput(String userId, String subject, Map<String, Object> claims) {
        this(userId, subject);
        this.claims = claims;
    }
}
