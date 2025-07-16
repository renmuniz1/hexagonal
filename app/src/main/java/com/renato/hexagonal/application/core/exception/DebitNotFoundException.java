package com.renato.hexagonal.application.core.exception;

public class DebitNotFoundException extends RuntimeException{

    public DebitNotFoundException(String id) {
        super("Débito com ID " + id + " não encontrado.");
    }

}
