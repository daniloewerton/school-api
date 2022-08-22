package com.daniloewerton.com.school.services.exception;

public class StudentAlreadyEnrolled extends RuntimeException {

    public StudentAlreadyEnrolled(String message) {
        super(message);
    }
}
