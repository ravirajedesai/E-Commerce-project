package com.example.cart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundGlobalExceptionHandler {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> UserNotFoundHandler(UserNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<String> ProductNotFoundHandler(ProductNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<String> cartNotFoundExceptionHandler(CartNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
