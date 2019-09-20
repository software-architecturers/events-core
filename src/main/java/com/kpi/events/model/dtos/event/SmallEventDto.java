package com.kpi.events.model.dtos.event;

import com.kpi.events.model.dtos.user.SmallUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SmallEventDto {
    private long id;

    private String title;

    private String description;

    private List<String> imagesLinks;

    private long likes;

    private boolean liked;

    private boolean visited;

    private boolean mine;

    private SmallUserDto creator;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
