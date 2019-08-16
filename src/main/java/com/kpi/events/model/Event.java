package com.kpi.events.model;

import com.kpi.events.model.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@Table(name = "events_table")
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class Event {
//Todo: creation date, start date, end date, likes, users that will go(first subscribed), creator, location

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

//    private User creator;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<User> visitors;

    private String description;

    @Embedded
    private LocationDto location;

    @OneToMany(cascade= {ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="image_id")
    private Set<Image> images;

    public Event() {
    }

    public Event(long id, String title,/* User creator,*/ LocalDateTime creationDate, LocalDateTime startDate,
                 LocalDateTime endDate, /*List<User> visitors,*/ String description, LocationDto location,
                 Set<Image> images) {
        this.id = id;
        this.title = title;
//        this.creator = creator;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
//        this.visitors = visitors;
        this.description = description;
        this.location = location;
        this.images = images;
    }

    public Event(long id, String title, String description, Set<Image> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
    }
}