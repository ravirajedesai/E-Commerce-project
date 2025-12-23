package com.example.user_service.exception;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message) {
        super(message);
    }
}
