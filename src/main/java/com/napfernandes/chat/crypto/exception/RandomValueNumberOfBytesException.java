package com.napfernandes.chat.crypto.exception;

public class RandomValueNumberOfBytesException extends Exception {
    public RandomValueNumberOfBytesException(int numberOfBytes) {
        super("Invalid number of bytes for generating random value (%s).".formatted(numberOfBytes));
    }
}
