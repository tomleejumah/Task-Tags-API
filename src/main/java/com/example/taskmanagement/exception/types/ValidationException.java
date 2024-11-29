package com.example.taskmanagement.exception.types;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
