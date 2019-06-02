package com.kpi.events.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommandantNumber() {
        return commandantNumber;
    }

    public void setCommandantNumber(String commandantNumber) {
        this.commandantNumber = commandantNumber;
    }

    public int getHostelNumber() {
        return hostelNumber;
    }

    public void setHostelNumber(int hostelNumber) {
        this.hostelNumber = hostelNumber;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
