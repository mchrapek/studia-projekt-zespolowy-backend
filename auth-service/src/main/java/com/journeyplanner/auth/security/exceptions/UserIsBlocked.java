package com.journeyplanner.auth.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserIsBlocked extends RuntimeException {

    public UserIsBlocked(String message) {
        super(message);
    }
}
