package com.leonpahole.workoutapp.errors;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 2768926439223995649L;

    public ApplicationException(String message) {
        super(message);
    }

    public HttpStatus getResponseCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}