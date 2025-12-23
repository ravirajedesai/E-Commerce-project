package com.example.order_service.exception;

public class CartNotFound extends RuntimeException{
    public CartNotFound(String message) {
        super(message);
    }
}
