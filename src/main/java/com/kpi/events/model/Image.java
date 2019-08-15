package com.kpi.events.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "images_table")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String link;

}
