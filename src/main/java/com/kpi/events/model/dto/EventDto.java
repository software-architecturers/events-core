package com.kpi.events.model.dto;

import java.util.List;

public class EventDto {

    private long id;

    private String title;

    private List<String> imagesLinks;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImagesLinks() {
        return imagesLinks;
    }

    public void setImagesLinks(List<String> imagesLinks) {
        this.imagesLinks = imagesLinks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
