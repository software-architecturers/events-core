package com.kpi.events.controllers;

import com.kpi.events.model.dto.RegisteredUserDto;
import com.kpi.events.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path = "/users")
    public List<RegisteredUserDto> getRegisteredUsers() {
       return userService.getUsers();
    }

}
