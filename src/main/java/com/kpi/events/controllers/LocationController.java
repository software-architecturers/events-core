package com.kpi.events.controllers;

import com.kpi.events.model.Event;
import com.kpi.events.model.dto.EventDto;
import com.kpi.events.model.dto.LocationDto;
import com.kpi.events.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/maps")
public class LocationController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public List<EventDto> getEventsOnMap(BigDecimal leftBotLatitude, BigDecimal leftBotLongitude,
                                         BigDecimal rightTopLatitude, BigDecimal rightTopLongitude) {

        return eventService
                .findEventsByLocation(
                        new LocationDto(leftBotLatitude, leftBotLongitude),
                        new LocationDto(rightTopLatitude, rightTopLongitude));
    }
}
