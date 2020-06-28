package com.leonpahole.workoutapp.errors;

import org.springframework.http.HttpStatus;

public class UserWithEmailExistsException extends ApplicationException {

    private static final long serialVersionUID = 4162836035174494038L;

    public UserWithEmailExistsException(String message) {
        super(message);
    }

    public HttpStatus getResponseCode() {
        return HttpStatus.CONFLICT;
    }
}