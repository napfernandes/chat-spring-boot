package com.napfernandes.chat.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorServiceImpl<T> implements ValidatorService<T> {
    @Autowired
    private Validator validator;

    @Override
    public boolean validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return true;
    }
}