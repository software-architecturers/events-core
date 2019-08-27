package com.kpi.events.controllers;

import com.kpi.events.model.dto.FollowedUserDto;
import com.kpi.events.model.dto.RegisteredUserDtoWithToken;
import com.kpi.events.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path = "/users")
    public List<RegisteredUserDtoWithToken> getRegisteredUsers() {
        return userService.getUsers();
    }


        @PutMapping(path = "/subscribe/{id}")
    public FollowedUserDto subscribe(@PathVariable("id") long userId) {
        return userService.subscribeOnUser(userId);
    }
}
