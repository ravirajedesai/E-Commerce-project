package com.example.order_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderNotFoundGlobalHandler {
    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<String> OrderNotFoundGlobalExceptionHandler(OrderNotFound ex){
        return ResponseEntity.ok(ex.getMessage());
    }
    @ExceptionHandler(CartNotFound.class)
    public ResponseEntity<String> CartNotFoundGlobalExceptionHandler(CartNotFound ex){
        return ResponseEntity.ok(ex.getMessage());
    }
}
