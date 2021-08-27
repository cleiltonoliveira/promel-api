package com.promel.api.exception;

public class InternalErrorException extends RuntimeException {

    private String error;

    public InternalErrorException(String message) {
        super(message);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
