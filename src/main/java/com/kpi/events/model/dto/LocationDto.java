package com.kpi.events.model.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
public class LocationDto {

//    широта
//    @Column(precision = 4, scale = 8)
    private BigDecimal latitude;
//    длина

//    @Column(precision = 4, scale = 8)
    private BigDecimal longitude;

    private String destination;

    public LocationDto(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationDto(){

    }

    public LocationDto(BigDecimal latitude, BigDecimal longitude, String destination) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.destination = destination;
    }
}
