package com.promel.api.usecase.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceConflictException extends RuntimeException {

    private String details;

    public ResourceConflictException(String details) {
        this.details = details;
    }
}