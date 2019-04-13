package com.kpi.events.model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Faculty {
    @Id
    private int id;

    @Column(name = "faculty_name", nullable = false)
    private String name;

//    @Column(name = "location", nullable = true)
    @OneToOne
    private Location location;
//
//    @OneToMany
//    private List<Group> groups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
//
//    public List<Group> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(List<Group> groups) {
//        this.groups = groups;
//    }
}
