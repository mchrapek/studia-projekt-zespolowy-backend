package com.journeyplanner.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NoPermission extends RuntimeException {

    public NoPermission(String message) {
        super(message);
    }
}
