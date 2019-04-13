package com.kpi.events.controllers;

import com.kpi.events.model.Event;
import com.kpi.events.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping("/api/events/add")
    public Event createNewEvent(@RequestBody Event event) {
        return service.save(event);
    }

    @GetMapping("/api/events")
    public List<Event> getEvents(@RequestParam int limit, @RequestParam int page) {
        return service.findAll(limit, page);
    }

    @GetMapping(path = "/api/events/{id}")
    public Event getEvent(@PathVariable("id") long eventId) {
        return service.find(eventId);
    }

    @PutMapping(path = "/api/events/update/{id}")
    public Event updateEvent(@PathVariable("id") long eventId,
                             @RequestBody Event newEvent) {
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
}