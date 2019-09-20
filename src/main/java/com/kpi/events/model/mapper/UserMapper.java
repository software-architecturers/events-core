package com.kpi.events.model.mapper;

import com.kpi.events.model.User;
import com.kpi.events.model.dtos.event.EventDto;
import com.kpi.events.model.dtos.user.FullUserDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import com.kpi.events.model.dtos.development.RegisteredUserDtoWithToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static final int SHOWN_LIMIT = 3;

    @Autowired
    private EventMapper eventMapper;

    public FullUserDto convertToFullUserDto(User user) {
        return FullUserDto.builder()
                .id(user.getId())
                .image(user.getImage())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .email(user.getEmail())
                .subscribers(user.getSubscribers()
                        .stream()
                        .map(this::convertToSmallUserDto)
                        .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                .subscribersCount(user.getSubscribers().size())
                .subscriptions(user.getSubscriptions()
                        .stream()
                        .map(this::convertToSmallUserDto)
                        .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                .subscriptionsCount(user.getSubscriptions().size())
                .subscribed(checkIfSubscribed(user))
                .createdEvents(user.getCreatedEvents()
                        .stream()
                        .map(event -> eventMapper.convertToDto(event, user))
                        .sorted(Comparator.comparing(EventDto::getCreationDate))
                        .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                .visitedEvents(user.getVisitedEvents()
                        .stream()
                        .map(event -> eventMapper.convertToDto(event, user))
                        .sorted(Comparator.comparing(EventDto::getStartDate))
                        .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                .build();
    }

    private boolean checkIfSubscribed(User user) {
        User requester = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getSubscribers().stream()
                .anyMatch(subscriber -> subscriber.getId() == requester.getId());
    }

    public SmallUserDto convertToSmallUserDto(User user) {
        return SmallUserDto.builder()
                .id(user.getId())
                .image(user.getImage())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .email(user.getEmail())
                .login(user.getLogin())
                .subscribed(checkIfSubscribed(user))
                .subscribers(user.getSubscribers().size())
                .subscriptions(user.getSubscriptions().size())
                .build();
    }

    public RegisteredUserDtoWithToken convertToRegisteredUserDtoWithToken(User user) {
        return RegisteredUserDtoWithToken.builder()
                .userDto(convertToSmallUserDto(user))
                .userToken(user.getRefreshToken())
                .build();
    }
}
