package com.napfernandes.chat.service;

public interface ValidatorService<T> {
    boolean validate(T objectToValidate);
}