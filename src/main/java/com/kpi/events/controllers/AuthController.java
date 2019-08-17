package com.kpi.events.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.kpi.events.model.User;
import com.kpi.events.security.models.RegisterDTO;
import com.kpi.events.security.models.Token;
import com.kpi.events.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;


@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping(value = "/auth/sign_up")
//    public String signUp(@RequestBody User user) {
//        String token = userService.signUp(user);
//        return UriComponentsBuilder.fromUriString("http://localhost:8080/home")
//                .queryParam("auth_token", token)
//                .build().toUriString();
//    }

    @PostMapping(value = "/auth/register")
    public String register(@RequestBody RegisterDTO user) {
        System.out.println("register ");
        System.out.println(user);
        userService.register(user);
        return "";
    }

    @PostMapping(value = "/auth/login")
    public Token login(@RequestBody User user) {
        return new Token(userService.login(user));
    }

    @PostMapping(value = "/auth/token")
    public Token refreshToken(@RequestBody Token tokenIn) {
        return new Token(userService.refresh(tokenIn));
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