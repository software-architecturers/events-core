package com.kpi.events.controllers;

import com.kpi.events.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static String USER_NOT_FOUND = "User not found";

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleMyException(UserNotFoundException ex, WebRequest req) {
        return handleExceptionInternal(ex, USER_NOT_FOUND, new HttpHeaders(), HttpStatus.FORBIDDEN, req);
    }
}
