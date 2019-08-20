package com.kpi.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
