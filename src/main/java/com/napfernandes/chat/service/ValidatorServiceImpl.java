package com.napfernandes.chat.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class ValidatorServiceImpl<T> implements ValidatorService<T> {
    private final Validator validator;

    public ValidatorServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return true;
    }
}