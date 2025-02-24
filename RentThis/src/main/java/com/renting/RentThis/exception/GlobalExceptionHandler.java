package com.renting.RentThis.exception;

import com.renting.RentThis.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ALL exceptions in one method
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
        ApiResponse<Object> errorResponse;

        // Custom handling for specific exceptions
        if (ex instanceof MethodArgumentNotValidException) {
            // Handle validation errors
            Map<String, String> errors = new HashMap<>();
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            errorResponse = ApiResponse.<Object>builder()
                    .success(false)
                    .data(errors)
                    .message("Validation failed")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        } else {
            // Handle all other exceptions
            errorResponse = ApiResponse.<Object>builder()
                    .success(false)
                    .data(null)
                    .message(ex.getMessage())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}