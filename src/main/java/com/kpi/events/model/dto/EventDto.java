package com.kpi.events.model.dto;

import lombok.Builder;
import lombok.Data;

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

    private boolean isLiked;

    private EventVisitorsDto visitors;

    private UserVisitorEventDto creator;
}
