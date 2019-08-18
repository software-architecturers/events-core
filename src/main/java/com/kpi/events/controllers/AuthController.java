package com.kpi.events.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.kpi.events.security.models.TokenResponse;
import com.kpi.events.model.RefreshToken;
import com.kpi.events.model.User;
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
    
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST, produces = "application/json")
    public TokenResponse login(@RequestBody User user) {
        System.out.println("login ");
        TokenResponse token = userService.login(user);
        return token;
    }

    @RequestMapping(value = "/auth/token", method = RequestMethod.POST, produces = "application/json")
    public TokenResponse refreshToken(@RequestBody RefreshToken tokenIn) {
        System.out.println("refresh ");

        TokenResponse token = userService.refresh(tokenIn);

        return token;
    }
	

    
    @GetMapping("/auth/suc")
    public String successful() {
        return "suc";
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