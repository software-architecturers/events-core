package com.kpi.events.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Image> images;


    public Event() {
    }

    public Event(long id, String title, Collection<Image> images, String description) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.description = description;
    }
}