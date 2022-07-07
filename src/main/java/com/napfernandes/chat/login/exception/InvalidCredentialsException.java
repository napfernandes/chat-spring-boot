package com.napfernandes.chat.login.exception;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Invalid username and/or password.");
    }
}
