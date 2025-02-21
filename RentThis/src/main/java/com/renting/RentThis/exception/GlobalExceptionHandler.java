package com.renting.RentThis.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?>handleRuntimeException(RuntimeException ex){
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
