package com.kpi.events.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class EventDto {

    private long id;

    private String title;

    private List<String> imagesLinks;

    private String description;

}
