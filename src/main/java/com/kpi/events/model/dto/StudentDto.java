package com.kpi.events.model.dto;

public class StudentDto {

    private long userId;

    private long studentId;

    private String firstName;

    private String secondName;

    private int facultyNumber;

    private int hostelNumber;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(int facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public int getHostelNumber() {
        return hostelNumber;
    }

    public void setHostelNumber(int hostelNumber) {
        this.hostelNumber = hostelNumber;
    }
}
