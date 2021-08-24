package com.promel.api.error;

import lombok.Getter;
import lombok.Setter;

public class InternalErrorException extends RuntimeException {

    @Getter
    @Setter
    private String error;

    public InternalErrorException(String message) {
        super(message);
    }
}
