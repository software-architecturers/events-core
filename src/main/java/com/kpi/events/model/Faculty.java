package com.kpi.events.model;

import javax.persistence.*;
import java.util.List;

//@Entity
public class Faculty {
//    @Id
//    @GeneratedValue
    private int id;

//    @Column(name = "faculty_name", nullable = false)
    private String name;

//    @OneToMany(mappedBy = "faculty", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @OneToMany
    private List<Group> groups;

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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
