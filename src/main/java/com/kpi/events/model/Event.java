package com.kpi.events.model;

import com.kpi.events.model.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@Table(name = "events_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    private User creator;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToMany
    @Builder.Default
    private Set<User> likes = new HashSet<>();

    @ManyToMany
    @Builder.Default
    private Set<User> visitors = new HashSet<>();;

    private String description;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "longitude", column = @Column(scale = 14, precision = 18)),
            @AttributeOverride(name = "latitude", column = @Column(scale = 14, precision = 18))
    })
    private LocationDto location;

    @OneToMany(cascade = {ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Set<Image> images;

}