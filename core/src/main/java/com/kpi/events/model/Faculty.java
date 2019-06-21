package com.kpi.events.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int facultyNumber;

    @Column(name = "faculty_name", nullable = false)
    private String name;

    private String imageName;

    private String deaneryNumber;

}
