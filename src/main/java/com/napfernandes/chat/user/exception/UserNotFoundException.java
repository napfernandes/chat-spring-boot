package com.napfernandes.chat.user.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String userId) {
        super(String.format("User (%s) not found.", userId));
        System.out.println("====== " + userId + "======");
    }
}
