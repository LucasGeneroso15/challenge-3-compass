package com.compass.msusers.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;

public class JwtGenerationTokenException extends RuntimeException {
    public JwtGenerationTokenException(String errorWhileGeneratingToken, JWTCreationException exception) {
        super(errorWhileGeneratingToken, exception);
    }
}
