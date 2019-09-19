package com.kpi.events.model.dtos.feature;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
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
}

