package com.jobintech.jobboardapi.exception;

// 409 - déjà existant
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
