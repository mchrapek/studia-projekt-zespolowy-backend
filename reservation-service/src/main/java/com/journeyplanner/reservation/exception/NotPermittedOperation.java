package com.journeyplanner.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotPermittedOperation extends RuntimeException {

    public NotPermittedOperation(String message) {
        super(message);
    }
}
