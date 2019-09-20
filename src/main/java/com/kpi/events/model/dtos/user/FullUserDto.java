package com.kpi.events.model.dtos.user;

import com.kpi.events.model.Image;
import com.kpi.events.model.dtos.event.EventDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullUserDto {
    private long id;

    private String login;

    private String email;

    private String firstName;

    private String secondName;

    private Image image;

    private Integer subscribersCount;

    private Integer subscriptionsCount;

    private List<SmallUserDto> subscribers;

    private List<SmallUserDto> subscriptions;

    private List<EventDto> visitedEvents;

    private List<EventDto> createdEvents;

    private boolean subscribed;
}

