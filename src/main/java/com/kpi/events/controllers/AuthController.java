package com.kpi.events.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kpi.events.security.models.TokenResponse;
import com.kpi.events.model.RefreshToken;
import com.kpi.events.model.User;
import com.kpi.events.security.models.RegisterDTO;
import com.kpi.events.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth/register")
    public String register(@RequestBody RegisterDTO user) {
        System.out.println("register ");
        userService.register(user);
        return "";
    }
    
    @PostMapping(value = "/auth/login")
    public TokenResponse login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping(value = "/auth/token")
    public TokenResponse refreshToken(@RequestBody RefreshToken tokenIn) {
        System.out.println("refresh ");

        TokenResponse token = userService.refresh(tokenIn);

        return token;
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<?> handleExpired(ExpiredJwtException e) {
    	return ResponseEntity.status(401).body("{\"error\": \"" + e.getMessage() + "\"}");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleException(RuntimeException e) {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + e.getMessage() + "\"}");
    }
}