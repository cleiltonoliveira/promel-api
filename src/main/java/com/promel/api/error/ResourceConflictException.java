package com.promel.api.error;

import lombok.Getter;
import lombok.Setter;

public class ResourceConflictException extends RuntimeException {

    @Getter
    @Setter
    private String error;

    public ResourceConflictException(String message) {
        super(message);
    }

}