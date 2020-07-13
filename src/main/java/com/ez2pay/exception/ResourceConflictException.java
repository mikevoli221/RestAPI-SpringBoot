package com.ez2pay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String exception) {
        super(exception);
    }
}