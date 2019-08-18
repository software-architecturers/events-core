package com.kpi.events.model;

import com.kpi.events.model.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@Table(name = "events_table")
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
//Todo: likes, users that will go(first subscribed), creator, location

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

//    private User creator;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToMany
    private Set<User> likes;
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<User> visitors;

    private String description;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "longitude", column = @Column(scale = 14, precision = 18)),
            @AttributeOverride(name = "latitude", column = @Column(scale = 14, precision = 18))
    })
    private LocationDto location;

    @OneToMany(cascade= {ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="image_id")
    private Set<Image> images;

    public Event() {
    }

    public Event(long id, String title, Set<User> likes,/* User creator,*/ LocalDateTime creationDate, LocalDateTime startDate,
                 LocalDateTime endDate, /*List<User> visitors,*/ String description, LocationDto location,
                 Set<Image> images) {
        this.id = id;        this.title = title;
//        this.likes =
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