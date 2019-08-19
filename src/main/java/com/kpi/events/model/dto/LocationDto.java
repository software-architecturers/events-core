package com.kpi.events.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDto {

    //широта
    private BigDecimal latitude;
    //длина
    private BigDecimal longitude;

    private String destination;

    public LocationDto(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationDto(BigDecimal latitude, BigDecimal longitude, String destination) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.destination = destination;
    }
}
