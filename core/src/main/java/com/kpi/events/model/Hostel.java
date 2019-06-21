package com.kpi.events.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Hostel {

    @Id
    private long id;

    @Column
    private String commandantNumber;

    @Column
    private int hostelNumber;

    @Column
    private String imageName;

}
