package com.journeyplanner.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalOperation extends RuntimeException {

    public IllegalOperation() {
        super();
    }

    public IllegalOperation(String message) {
        super(message);
    }
}
