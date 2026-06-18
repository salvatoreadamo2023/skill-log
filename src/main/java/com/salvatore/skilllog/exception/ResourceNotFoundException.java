package com.salvatore.skilllog.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " non trovato con id: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
