package com.jobintech.jobboardapi.exception;

// 403 - pas le droit
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
