package com.backtostock.exception;

public class EmptyEntity extends RuntimeException {
    public EmptyEntity(String message) {
        super(message);
    }
}
