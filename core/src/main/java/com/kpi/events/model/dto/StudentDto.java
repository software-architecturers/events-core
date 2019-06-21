package com.kpi.events.model.dto;


import lombok.Data;

@Data
public class StudentDto {

    private long userId;

    private long studentId;

    private String firstName;

    private String secondName;

    private int facultyNumber;

    private int hostelNumber;

}
