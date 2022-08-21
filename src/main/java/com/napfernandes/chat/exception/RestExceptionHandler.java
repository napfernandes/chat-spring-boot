package com.napfernandes.chat.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.napfernandes.chat.conversation.exception.ConversationNotFoundException;
import com.napfernandes.chat.login.exception.InvalidCredentialsException;
import com.napfernandes.chat.user.exception.UserAlreadyExistsException;
import com.napfernandes.chat.user.exception.UserNotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(String exceptionMessage, HttpStatus httpStatus) {
        ApiException apiException = new ApiException(exceptionMessage, httpStatus, "");
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException) {
        return buildResponseEntity(userAlreadyExistsException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException) {
        return buildResponseEntity(constraintViolationException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    protected ResponseEntity<Object> handleInvalidCredentialsException(
            InvalidCredentialsException invalidCredentialsException) {
        return buildResponseEntity(invalidCredentialsException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversationNotFoundException.class)
    protected ResponseEntity<Object> handleConversationNotFoundException(
            ConversationNotFoundException conversationNotFoundException) {
        return buildResponseEntity(conversationNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {
        return buildResponseEntity(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}