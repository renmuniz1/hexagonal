package com.renato.hexagonal.config.shared;

import com.renato.hexagonal.application.core.exception.DebitNotFoundException;
import com.renato.hexagonal.application.core.exception.InfrastructureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DebitNotFoundException.class)
    public ResponseEntity<String> handleNotFound(DebitNotFoundException ex) {
        System.out.println("ERRO -> " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<String> handleInfra(InfrastructureException ex) {
        System.out.println("ERRO -> " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        System.out.println("ERRO -> " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }


}
