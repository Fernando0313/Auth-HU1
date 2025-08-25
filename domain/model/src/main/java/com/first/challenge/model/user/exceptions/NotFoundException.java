package com.first.challenge.model.user.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}