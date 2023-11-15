package com.bugtrackingsystem.exceptions;

//@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Resource not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
