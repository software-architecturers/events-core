package com.kpi.events.model.mapper;

import com.kpi.events.model.User;
import com.kpi.events.model.dtos.user.FullUserDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import com.kpi.events.model.dtos.development.RegisteredUserDtoWithToken;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public FullUserDto convertToFullUserDto(User persistedUser) {
        return FullUserDto.builder()
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

    public SmallUserDto convertToRegisteredDto(User user) {
        return SmallUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .email(user.getEmail())
                .login(user.getLogin())
                .build();
    }

    public RegisteredUserDtoWithToken convertToRegisteredUserDtoWithToken(User user) {
        return RegisteredUserDtoWithToken.builder()
                .userDto(convertToRegisteredDto(user))
                .userToken(user.getRefreshToken())
                .build();
    }
}
