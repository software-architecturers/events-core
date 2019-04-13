package com.kpi.events.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
//TODO: add one-to-many annotation
//TODO: add table annotation

@Entity
public class Student {

    @OneToOne
    private User user;

    @Id
    private int id;

    @NotNull
    private long studentTicket;
//
//    @ManyToOne
//    private Group group;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStudentTicket() {
        return studentTicket;
    }

    public void setStudentTicket(long studentTicket) {
        this.studentTicket = studentTicket;
    }

//    public Group getGroup() {
//        return group;
//    }
//
//    public void setGroup(Group group) {
//        this.group = group;
//    }
}