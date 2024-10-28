package com.compass.msusers.exceptions.handler;

import com.compass.msusers.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundException(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UniqueViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, "Unique Violation Exception"));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({InvalidZipCodeException.class, PasswordInvalidException.class, EqualPasswordInvalidException.class})
    public ResponseEntity<ErrorMessage> badRequestException(RuntimeException e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
