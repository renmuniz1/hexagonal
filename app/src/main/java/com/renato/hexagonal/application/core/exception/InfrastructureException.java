package com.renato.hexagonal.application.core.exception;

public class InfrastructureException extends RuntimeException {

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

}
