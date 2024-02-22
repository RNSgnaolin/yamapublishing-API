package com.yama.publishing.infra.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundTreatment() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DataErrorValidation>> notValid(MethodArgumentNotValidException ex) {

        List<FieldError> errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataErrorValidation::new).toList());

    }

    public ResponseEntity<String> badCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private record DataErrorValidation(String field, String message) {

        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }

    }
    
}