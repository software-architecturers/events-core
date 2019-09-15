package com.kpi.events.model.dto;

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

    private LocationDto locationDto;

    private long likes;

    private boolean liked;

    private boolean visited;

    private boolean mine;

    private EventVisitorsDto visitors;

    private PersonalCabinetDto creator;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
