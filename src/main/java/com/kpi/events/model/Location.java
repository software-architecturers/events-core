package com.kpi.events.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private double latitude;

    @NotNull
    private double longtitude;
}
