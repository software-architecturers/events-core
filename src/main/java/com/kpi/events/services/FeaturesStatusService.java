package com.kpi.events.services;

import com.kpi.events.model.dto.FeaturesStatusDto;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeaturesStatusService {

    @Autowired
    private FeaturesStatusDto featuresStatusDto;

    public Object getAllFeaturesStatus() {
        JSONObject responseObject = new JSONObject();
        List<JSONObject> eventsStatusList = new ArrayList<>();
        JSONObject eventsStatus = new JSONObject();
        eventsStatus.put("features.events.Main", featuresStatusDto.getEventStatus());
        eventsStatus.put("isActive", featuresStatusDto.getEventStatus());
        eventsStatus.put("request", "http://goggle.com");
        eventsStatus.put("titleName", "Все ивенты");
        eventsStatus.put("logoImageURL", "http://..");
        eventsStatusList.add(eventsStatus);

        JSONObject eventsMissKpiStatus = new JSONObject();
        eventsMissKpiStatus.put("features.events.Miss_Kpi", featuresStatusDto.getEventMissKpiStatus());
        eventsMissKpiStatus.put("isActive", featuresStatusDto.getEventMissKpiStatus());
        eventsMissKpiStatus.put("request", "http://goggle.com");
        eventsMissKpiStatus.put("titleName", "Мисс КПИ");
        eventsMissKpiStatus.put("logoImageURL", "http://..");
        eventsStatusList.add(eventsMissKpiStatus);

        responseObject.put("events", eventsStatusList);

        List<JSONObject> otherStatusList = new ArrayList<>();

        JSONObject university = new JSONObject();
        university.put("features.university", featuresStatusDto.getUniversityStatus());

        otherStatusList.add(university);
        JSONObject students = new JSONObject();
        students.put("features.students", featuresStatusDto.getStudentStatus());

        otherStatusList.add(students);
        responseObject.put("other", otherStatusList);

        JSONObject object = new JSONObject();
        object.put("response", responseObject);
        return object;
    }
}