package com.example.product_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<String>GlobalExceptionProductNotFound(ProductNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
