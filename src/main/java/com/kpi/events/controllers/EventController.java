package com.kpi.events.controllers;

import com.kpi.events.model.Event;
import com.kpi.events.model.User;
import com.kpi.events.model.dtos.event.EventDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import com.kpi.events.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@ConditionalOnProperty(name = "features.events.common")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping("/events")
    public List<EventDto> getEvents(@RequestParam(required = false, defaultValue = "10") int limit,
                                    @RequestParam(required = false, defaultValue = "0") int page) {
        return service.findAll(limit, page);
    }

    @GetMapping(path = "/events/{id}")
    public Event getEvent(@PathVariable("id") long eventId) {
        return service.find(eventId);
    }

    @PutMapping("/events/like/{id}")
    public EventDto likeEvent(@PathVariable(name = "id") long eventId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.likeEvent(user, eventId);
    }

    @GetMapping(path = "/events/find")
    public List<EventDto> searchEvents(@RequestParam("q") String search,
                                       @RequestParam(required = false, defaultValue = "10") int limit,
                                       @RequestParam(required = false, defaultValue = "0") int page) {
        return service.searchEventLIKEGOOGLE(search, limit, page);
    }

    @PutMapping("/events/visit/{id}")
    public EventDto visitEvent(@PathVariable(name = "id") long eventId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.visitEvent(user, eventId);
    }

    @GetMapping(path = "/events/{id}/visitors")
    public  List<SmallUserDto>  getEventVisitors(@PathVariable("id") long eventId) {
        return service.getEventVisitors(eventId);
    }

    @PostMapping("/events/add")
    public EventDto createNewEvent(@RequestBody Event event) {
        return service.save(event);
    }

    @PutMapping(path = "/events/update/{id}")
    public Event updateEvent(@PathVariable("id") long eventId, @RequestBody Event newEvent) {
        return service.update(eventId, newEvent);
    }

    @DeleteMapping(path = "/events/delete/{id}")
    public void deleteEvent(@PathVariable("id") long id) {
        service.delete(id);
    }

}