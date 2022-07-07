package com.napfernandes.chat.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiException {
    private String message;
    private int statusCode;
    private List<String> stackTraces;

    public ApiException(String message, HttpStatus status, String stackTrace) {
        super();
        this.message = message;
        this.statusCode = status.value();
        stackTraces = Arrays.asList(stackTrace);
    }
}