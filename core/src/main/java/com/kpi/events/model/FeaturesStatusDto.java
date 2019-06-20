package com.kpi.events.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Data
@Component
public class FeaturesStatusDto {
    @Value("${features.events.common}")
    private String eventStatus;

    @Value("${features.events.miss}")
    private String eventMissKpiStatus;

    @Value("${features.university}")
    private String studentStatus;

    @Value("${features.students}")
    private String universityStatus;

    public FeaturesStatusDto() {

    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventMissKpiStatus() {
        return eventMissKpiStatus;
    }

    public void setEventMissKpiStatus(String eventMissKpiStatus) {
        this.eventMissKpiStatus = eventMissKpiStatus;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getUniversityStatus() {
        return universityStatus;
    }

    public void setUniversityStatus(String universityStatus) {
        this.universityStatus = universityStatus;
    }
}

