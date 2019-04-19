package com.kpi.events.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.kpi.events.model.UserIn;
import com.kpi.events.services.UserService;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping("/auth/custom-login")
    public String login() {
        System.out.println("login");
        return "login";
    }

    @PostMapping("/auth/signup")
    public String signup(UserIn user) {
        System.out.println("signup ");
        String token = userService.signUp(user);
        return UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .queryParam("auth_token", token)
                .build().toUriString();
    }
}