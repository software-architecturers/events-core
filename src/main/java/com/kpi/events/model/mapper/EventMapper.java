package com.kpi.events.model.mapper;

import com.kpi.events.model.Event;
import com.kpi.events.model.Image;
import com.kpi.events.model.User;
import com.kpi.events.model.dtos.event.EventDto;
import com.kpi.events.model.dtos.event.SmallEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    private static final int VISITORS_SHOWN_LIMIT = 3;

    @Autowired
    private UserMapper userMapper;


    public EventDto convertToEventDto(Event event, User userRequester) {
        return EventDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .imagesLinks(event.getImages()
                        .stream()
                        .map(Image::getLink)
                        .collect(Collectors.toList()))
                .title(event.getTitle())
                .likes(event.getLikes().size())
                .location(event.getLocation())
                .liked(checkIfLiked(event, userRequester))
                .creationDate(event.getCreationDate())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .mine(checkIfMine(event, userRequester))
                .creator(userMapper.convertToSmallUserDto(event.getCreator()))
                .visited(checkIfVisited(event, userRequester))
                .visitors(EventDto.EventVisitorsDto.builder()
                        .users(event.getVisitors()
                                .stream()
                                .sorted(Comparator.comparingInt(user -> user.getCreatedEvents().size()))
                                .map(visitor -> userMapper.convertToSmallUserDto(visitor))
                                .limit(VISITORS_SHOWN_LIMIT)
                                .collect(Collectors.toList()))
                        .count(event.getVisitors().size())
                        .build())
                .build();
    }


    public SmallEventDto convertToSmallEventDto(Event event, User userRequester) {

        return SmallEventDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .imagesLinks(event.getImages()
                        .stream()
                        .map(Image::getLink)
                        .collect(Collectors.toList()))
                .title(event.getTitle())
                .likes(event.getLikes().size())
                .liked(checkIfLiked(event, userRequester))
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .mine(checkIfMine(event, userRequester))
                .creator(userMapper.convertToSmallUserDto(event.getCreator()))
                .visited(checkIfVisited(event, userRequester))
                .build();
    }

    private boolean checkIfVisited(Event event, User userRequester) {
        return event.getVisitors().contains(userRequester);
    }

    private boolean checkIfLiked(Event event, User userRequester) {
        return event.getLikes().contains(userRequester);
    }

    private boolean checkIfMine(Event event, User userRequester) {
        return event.getCreator().getId() == userRequester.getId();

    }
}
