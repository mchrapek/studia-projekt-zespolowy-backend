package com.journeyplanner.catalogue.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CannotParseFile extends RuntimeException {

    public CannotParseFile(String message) {
        super(message);
    }
}
