package com.promel.api.usecase.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String details;

    public ResourceNotFoundException(String details) {
        this.details = details;
    }
}
