package com.journeyplanner.user.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserWithEmailAlreadyExists extends RuntimeException {

    public UserWithEmailAlreadyExists(String message) {
        super(message);
    }
}
