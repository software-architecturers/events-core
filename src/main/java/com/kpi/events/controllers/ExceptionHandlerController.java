package com.kpi.events.controllers;

import com.kpi.events.exceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static String USER_NOT_FOUND = "User with specified data not found";

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleMyException(UserNotFoundException ex, WebRequest req) {
        return handleExceptionInternal(ex, USER_NOT_FOUND, new HttpHeaders(), HttpStatus.NO_CONTENT, req);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpired(ExpiredJwtException e) {
        return ResponseEntity.status(401).body("{\"Error\": \"" + e.getMessage() + "\"}");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"" + e.getMessage() + "\"}");
    }
}
