package com.kpi.events.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.kpi.events.security.models.TokenResponse;
import com.kpi.events.model.User;
import com.kpi.events.model.UserIn;
import com.kpi.events.security.filters.JwtTokenUtil;
import com.kpi.events.security.models.RegisterDTO;
import com.kpi.events.security.models.Token;
import com.kpi.events.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/auth/signup", method = RequestMethod.POST, produces = "application/json")
    public String signup(@RequestBody User user) {
        System.out.println("signup ");
        String token = userService.signUp(user);
        return UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .queryParam("auth_token", token)
                .build().toUriString();
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST, produces = "application/json")
    public String register(@RequestBody RegisterDTO user) {
        System.out.println("register ");
        System.out.println(user);
        userService.register(user);
        return "";
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST, produces = "application/json")
    public TokenResponse login(@RequestBody User user) {
        System.out.println("login ");
        String token = userService.login(user);
        return new TokenResponse(token);
    }

    @RequestMapping(value = "/auth/token", method = RequestMethod.POST, produces = "application/json")
    public TokenResponse refreshToken(@RequestBody Token tokenIn) {
        System.out.println("refresh ");

        String token = userService.refresh(tokenIn);

        return new TokenResponse(token);
    }



    @GetMapping("/auth/suc")
    public String successful() {
        return "suc";
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<?> handleExpred(ExpiredJwtException e) {
    	return ResponseEntity.status(401).body("{\"error\": \"" + e.getMessage() + "\"}");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleException(RuntimeException e) {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + e.getMessage() + "\"}");
    }
}