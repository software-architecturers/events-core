package com.kpi.events.model.dtos.user;

import com.kpi.events.model.Image;
import com.kpi.events.model.dtos.event.SmallEventDto;
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

    private Image image;

    private String login;

    private String email;

    private String firstName;

    private String secondName;

    private boolean subscribed;

    private UserSubscribersDto subscribers;

    private UserSubscriptionsDto subscriptions;

    private VisitedEventDto visitedEvents;

    private CreatedEventsDto createdEvents;

    @Data
    @Builder
    public static class CreatedEventsDto {
        private Integer count;

        private List<SmallEventDto> events;
    }

    @Data
    @Builder
    public static class VisitedEventDto {
        private Integer count;

        private List<SmallEventDto> events;
    }

    @Data
    @Builder
    public static class UserSubscribersDto {
        private Integer count;

        private List<SmallUserDto> users;
    }

    @Data
    @Builder
    public static class UserSubscriptionsDto {
        private Integer count;

        private List<SmallUserDto> users;
    }
}

