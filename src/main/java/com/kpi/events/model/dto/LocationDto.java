package com.kpi.events.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDto {

//    широта
    private BigDecimal latitude;
//    длина
    private BigDecimal longitude;

    private String destination;


}
