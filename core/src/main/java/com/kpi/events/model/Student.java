package com.kpi.events.model;

import org.springframework.beans.factory.annotation.Lookup;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Student {

    @Id
    private long id;

    @OneToOne
    private User user;

    @ManyToOne
    private Faculty faculty;

    @ManyToOne
    private Hostel hostel;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    public Hostel getHostel() {
        return Optional.ofNullable(hostel)
                .orElse(new Hostel());
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }
}