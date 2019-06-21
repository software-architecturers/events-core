package com.kpi.events.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Lookup;

import javax.persistence.*;
import java.util.Optional;

@Data
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

    public Hostel getHostel() {
        return Optional.ofNullable(hostel)
                .orElse(new Hostel());
    }
}