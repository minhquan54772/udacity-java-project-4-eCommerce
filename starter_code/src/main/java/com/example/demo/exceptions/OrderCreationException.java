package com.example.demo.exceptions;

public class OrderCreationException extends RuntimeException {
    public OrderCreationException(String message) {
        super(message);
    }
}