package com.napfernandes.chat.user.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String userIdOrEmail) {
        super("An user with the identifier (%s) already exists.".formatted(userIdOrEmail));
    }
}
