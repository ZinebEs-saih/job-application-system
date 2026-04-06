package com.jobintech.jobboardapi.exception;

// 404 - ressource introuvable
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
