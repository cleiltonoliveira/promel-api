package com.promel.api.usecase.exception;

public class ResourceConflictException extends RuntimeException {

    private String error;

    public ResourceConflictException(String message) {
        super(message);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}