package com.kpi.miss.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class MissKpiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String girlName;

    private String description;

    private Long likes;

    @OneToMany
    private Collection<GirlImage> images;

}