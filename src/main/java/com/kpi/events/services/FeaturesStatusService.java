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

        populateFeatures(eventsStatusList, "features.events.Main", featuresStatusDto.getEventStatus(), "Все ивенты");
//        populateFeatures(eventsStatusList, "features.events.Miss_Kpi", featuresStatusDto.getEventMissKpiStatus(), "Мисс КПИ");

        responseObject.put("events", eventsStatusList);
//        populateOtherFeatures(responseObject);
        JSONObject object = new JSONObject();
        object.put("response", responseObject);
        return object;
    }

    private void populateFeatures(List<JSONObject> eventsStatusList, String feature,
                                  String eventMissKpiStatus, String featureName) {
        JSONObject eventsMissKpiStatus = new JSONObject();
        eventsMissKpiStatus.put(feature, eventMissKpiStatus);
        eventsMissKpiStatus.put("isActive", eventMissKpiStatus);
        eventsMissKpiStatus.put("request", "https://events-core.herokuapp.com/api/events/");
        eventsMissKpiStatus.put("titleName", featureName);
        eventsMissKpiStatus.put("logoImageURL", "http://..");
        eventsStatusList.add(eventsMissKpiStatus);
    }

    private void populateOtherFeatures(JSONObject responseObject) {
        List<JSONObject> otherStatusList = new ArrayList<>();

        JSONObject university = new JSONObject();
        university.put("features.university", featuresStatusDto.getUniversityStatus());

        otherStatusList.add(university);
        JSONObject students = new JSONObject();
        students.put("features.students", featuresStatusDto.getStudentStatus());

        otherStatusList.add(students);
        responseObject.put("other", otherStatusList);
    }
}