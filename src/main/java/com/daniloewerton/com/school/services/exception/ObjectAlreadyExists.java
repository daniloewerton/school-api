package com.daniloewerton.com.school.services.exception;

public class ObjectAlreadyExists extends RuntimeException {

    public ObjectAlreadyExists(String message) {
        super(message);
    }
}
