package com.kpi.events.model.mapper;

import com.kpi.events.model.User;
import com.kpi.events.model.dto.FollowedUserDto;
import com.kpi.events.model.dto.PersonalCabinetDto;
import com.kpi.events.model.dto.RegisteredUserDto;
import com.kpi.events.model.dto.RegisteredUserDtoWithToken;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public PersonalCabinetDto convertToPersonalCabinetDto(User persistedUser) {
        return PersonalCabinetDto.builder()
                .id(persistedUser.getId())
                .login(persistedUser.getLogin())
                .firstName(persistedUser.getFirstName())
                .secondName(persistedUser.getSecondName())
                .email(persistedUser.getEmail())
                .image(persistedUser.getImage())
                .subscribers(persistedUser.getSubscribers().size())
                .subscriptions(persistedUser.getSubscriptions().size())
                .build();
    }


    public FollowedUserDto convertToFollowerDto(User userToFollow) {
        return FollowedUserDto.builder()
                .user(convertToRegisteredDto(userToFollow))
                .subscribers(userToFollow.getSubscribers().stream().map(this::convertToRegisteredDto).collect(Collectors.toList()))
                .subscriptions(userToFollow.getSubscriptions().stream().map(this::convertToRegisteredDto).collect(Collectors.toList()))
                .build();
    }

    public RegisteredUserDto convertToRegisteredDto(User user) {
        return RegisteredUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }

    public RegisteredUserDtoWithToken convertToRegisteredUserDtoWithToken(User user) {
        return RegisteredUserDtoWithToken.builder()
                .userDto(convertToRegisteredDto(user))
                .userToken(user.getRefreshToken())
                .build();
    }
}
