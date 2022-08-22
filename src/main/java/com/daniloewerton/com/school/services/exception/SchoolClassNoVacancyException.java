package com.daniloewerton.com.school.services.exception;

public class SchoolClassNoVacancyException extends RuntimeException {

    public SchoolClassNoVacancyException(String message) {
        super(message);
    }
}
