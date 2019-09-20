package com.kpi.events.model.mapper;

import com.kpi.events.model.User;
import com.kpi.events.model.dtos.event.EventDto;
import com.kpi.events.model.dtos.event.SmallEventDto;
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
                .subscribers(FullUserDto.UserSubscribersDto.builder()
                        .users(user.getSubscribers()
                                .stream()
                                .map(this::convertToSmallUserDto)
                                .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                        .count(user.getSubscribers().size())
                        .build())
                .subscriptions(FullUserDto.UserSubscriptionsDto.builder()
                        .users(user.getSubscriptions()
                                .stream()
                                .map(this::convertToSmallUserDto)
                                .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                        .count(user.getSubscriptions().size()).build())
                .subscribed(checkIfSubscribed(user))
                .createdEvents(FullUserDto.CreatedEventsDto.builder()
                        .events(user.getCreatedEvents()
                                .stream()
                                .map(event -> eventMapper.convertToSmallEventDto(event, user))
                                .sorted(Comparator.comparing(SmallEventDto::getStartDate))
                                .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                        .count(user.getCreatedEvents().size())
                        .build())
                .visitedEvents(FullUserDto.VisitedEventDto.builder()
                        .events(user.getVisitedEvents()
                                .stream()
                                .map(event -> eventMapper.convertToSmallEventDto(event, user))
                                .sorted(Comparator.comparing(SmallEventDto::getStartDate))
                                .limit(SHOWN_LIMIT).collect(Collectors.toList()))
                        .count(user.getVisitedEvents().size())
                        .build())
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
