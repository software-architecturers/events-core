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
    public ResponseEntity register(@RequestBody RegisterDTO user) {
        userService.register(user);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PostMapping(value = "/auth/login")
    public TokenResponse login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping(value = "/auth/token")
    public TokenResponse refreshToken(@RequestBody RefreshToken tokenIn) {
        return userService.refresh(tokenIn);
    }

}