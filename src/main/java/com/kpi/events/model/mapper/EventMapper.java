package com.kpi.events.model.mapper;

import com.kpi.events.model.Event;
import com.kpi.events.model.Image;
import com.kpi.events.model.User;
import com.kpi.events.model.dto.EventDto;
import com.kpi.events.model.dto.EventVisitorsDto;
import com.kpi.events.model.dto.UserVisitorEventDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventMapper {

    private static final int VISITORS_SHOWN_LIMIT = 3;


    public EventDto convertToDto(Event event, User userRequester) {
        return EventDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .imagesLinks(event.getImages()
                        .stream()
                        .map(Image::getLink)
                        .collect(Collectors.toList()))
                .title(event.getTitle())
                .likes(event.getLikes().size())
                .locationDto(event.getLocation())
                .liked(checkIfLiked(event, userRequester))
                .creationDate(event.getCreationDate())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .mine(checkIfMine(event, userRequester))
                .creator(UserVisitorEventDto.builder()
                        .id(event.getCreator().getId())
                        .login(event.getCreator().getLogin())
                        .email(event.getCreator().getEmail())
                        .firstName(event.getCreator().getFirstName())
                        .secondName(event.getCreator().getSecondName())
                        .build())
                .visited(checkIfVisited(event, userRequester))
                .visitors(new EventVisitorsDto(event.getVisitors()
                        .stream()
                        .limit(VISITORS_SHOWN_LIMIT)
                        .map(visitor ->
                            UserVisitorEventDto.builder()
                                    .login(visitor.getLogin())
                                    .email(visitor.getEmail())
                                    .firstName(visitor.getFirstName())
                                    .secondName(visitor.getSecondName())
                                    .build())
                        .collect(Collectors.toSet()),
                        event.getVisitors().size()))

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
