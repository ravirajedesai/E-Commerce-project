package com.example.product_service.exceptions;

public class InsufficientProductStock extends RuntimeException{
    public InsufficientProductStock(String message) {
        super(message);
    }
}
