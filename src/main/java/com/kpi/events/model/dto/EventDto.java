package com.kpi.events.model.dto;

import com.kpi.events.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class EventDto {

    private String title;

    private String description;

    private List<String> imagesLinks;

    private LocationDto locationDto;

    private long likes;

    private boolean isLiked;

    private EventVisitorsDto visitors;

    private UserVisitorEventDto creator;
}
