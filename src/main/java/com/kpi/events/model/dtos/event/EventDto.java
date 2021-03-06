package com.kpi.events.model.dtos.event;

import com.kpi.events.model.dtos.location.LocationDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventDto {
    private long id;

    private String title;

    private String description;

    private List<String> imagesLinks;

    private LocationDto location;

    private long likes;

    private boolean liked;

    private boolean visited;

    private boolean mine;

    private EventVisitorsDto visitors;

    private SmallUserDto creator;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Data
    @Builder
    public static class EventVisitorsDto {

        private List<SmallUserDto> users;

        private long count;
    }
}
