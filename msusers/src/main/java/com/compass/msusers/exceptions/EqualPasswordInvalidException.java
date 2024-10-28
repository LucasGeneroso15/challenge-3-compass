package com.compass.msusers.exceptions;

public class EqualPasswordInvalidException extends RuntimeException {
    public EqualPasswordInvalidException(String message) {
        super(message);
    }
}
