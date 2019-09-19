package com.kpi.events.model.dtos.event;

import com.kpi.events.model.dtos.location.LocationDto;
import com.kpi.events.model.dtos.user.FullUserDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class EventDto {
    private long id;

    private String title;

    private String description;

    private List<String> imagesLinks;

    private LocationDto locationDto;

    private long likes;

    private boolean liked;

    private boolean visited;

    private boolean mine;

    private EventVisitorsDto visitors;

    private FullUserDto creator;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventVisitorsDto {

        private Set<SmallUserDto> users;

        private long count;
    }
}
