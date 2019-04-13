package com.kpi.events.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private long id;

    private String info;
}
