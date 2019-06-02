package com.kpi.events.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @OneToMany(cascade= CascadeType.ALL)
    private Collection<Image> images;


    public Event() {
    }

    public Event(long id, String title, Collection<Image> images, String description) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.description = description;
    }

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

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
