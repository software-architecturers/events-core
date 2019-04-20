package com.kpi.events.controllers;

import com.kpi.events.model.Event;
import com.kpi.events.model.dto.EventDto;
import com.kpi.events.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class EventController {

    @Autowired
    private EventService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/api/events/add")
    public Event createNewEvent(@RequestBody Event event) {
        return service.save(event);
    }

    @GetMapping("/api/events")
    public List<Event> getEvents(@RequestParam(required = false, defaultValue = "10") int limit,
                                 @RequestParam(required = false, defaultValue = "0") int page) {
        return service.findAll(limit, page);
    }

    @GetMapping(path = "/api/events/{id}")
    public EventDto getEvent(@PathVariable("id") long eventId) {
        return convertToDto(service.find(eventId));
    }

    @PutMapping(path = "/api/events/update/{id}")
    public Event updateEvent(@PathVariable("id") long eventId, @RequestBody Event newEvent) {
        return service.update(eventId, newEvent);
    }

    @GetMapping(path = "/api/events/find/{search}")
    public List<Event> searchEvents(@PathVariable("search") String search) {
        return service.searchEventLIKEGOOGLE(search);
    }

    @DeleteMapping(path = "/api/events/delete/{id}")
    public void deleteEvent(@PathVariable("id") long id) {
        service.delete(id);
    }

    private EventDto convertToDto(Event event) {
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        eventDto.setImagesLinks(service.findImageLinks(event.getId()));
        return eventDto;
    }
}