package com.kpi.events.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.kpi.events.model.User;
import com.kpi.events.model.UserIn;
import com.kpi.events.security.filters.JwtTokenUtil;
import com.kpi.events.security.models.Token;
import com.kpi.events.services.UserService;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/auth/signup")
    public String signup(@RequestBody User user) {
        System.out.println("signup ");
        String token = userService.signUp(user);
        return UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .queryParam("auth_token", token)
                .build().toUriString();
    }
    
    @PostMapping("/auth/register")
    public String register(@RequestBody User user) {
        System.out.println("register ");
        System.out.println(user);
        userService.register(user);
        return UriComponentsBuilder.fromUriString("http://localhost:8080/auth/success")
                .build().toUriString();
    }
    
    @PostMapping("/auth/login")
    public String login(@RequestBody User user) {
        System.out.println("login ");
        String token = userService.login(user);
        return UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .queryParam("auth_token", token)
                .build().toUriString();
    }
    
    @PostMapping("/auth/token")
    public String refreshToken(@RequestBody Token tokenIn) {
        System.out.println("refresh ");
        
        String token = userService.refresh(tokenIn);
        
        return UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .queryParam("auth_token", token)
                .build().toUriString();
    }
	

    
    @GetMapping("/auth/suc")
    public String successful() {
        return "suc";
    }
}