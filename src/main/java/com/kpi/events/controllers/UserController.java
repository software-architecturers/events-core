package com.kpi.events.controllers;

import com.kpi.events.model.dtos.user.FullUserDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import com.kpi.events.model.dtos.development.RegisteredUserDtoWithToken;
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
    public FullUserDto subscribe(@PathVariable("id") long userId) {
        return userService.subscribeOnUser(userId);
    }

    @GetMapping(path = "users/me")
    public FullUserDto getInfoAboutMe() {
        return userService.getUserInfo();
    }

    @GetMapping(path = "users/{id}")
    public FullUserDto getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping(path = "users/{id}/subscribers")
    public List<SmallUserDto> getSubscribers(@PathVariable long id) {
        return userService.getSubscribers(id);
    }

    @GetMapping(path = "users/{id}/subscriptions")
    public List<SmallUserDto> getSubscriptions(@PathVariable long id) {
        return userService.getSubscriptions(id);
    }
}