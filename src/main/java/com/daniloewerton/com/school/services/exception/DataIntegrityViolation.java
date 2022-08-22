package com.daniloewerton.com.school.services.exception;

public class DataIntegrityViolation extends RuntimeException {

    public DataIntegrityViolation(String msg) {
        super(msg);
    }
}
