package com.promel.api.usecase.exception;

public class CustomForbiddenException extends RuntimeException {

    public CustomForbiddenException(String message) {
        super(message);
    }
}