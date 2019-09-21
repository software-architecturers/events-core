package com.kpi.events.controllers;

import com.kpi.events.model.Event;
import com.kpi.events.model.User;
import com.kpi.events.model.dtos.event.EventDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import com.kpi.events.services.EventService;
import com.kpi.events.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@ConditionalOnProperty(name = "features.events.common")
public class EventController {

    @Autowired
    private EventService service;

    @Autowired
    private UserService userService;

    @GetMapping("/events")
    public List<EventDto> getEvents(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping(path = "/events/{id}")
    public EventDto getEvent(@PathVariable("id") long eventId) {
        return service.find(eventId);
    }

    @GetMapping(path = "/events/find")
    public List<EventDto> searchEvents(@RequestParam("q") String search, Pageable pageable) {
        return service.searchEventLIKEGOOGLE(search, pageable);
    }

    @GetMapping(path = "/events/{id}/visitors")
    public List<SmallUserDto> getEventVisitors(@PathVariable("id") long eventId) {
        return service.getEventVisitors(eventId);
    }

    @PutMapping("/events/like/{id}")
    public EventDto likeEvent(@PathVariable(name = "id") long eventId) {
        User user = userService.getRequester();
        return service.likeEvent(user, eventId);
    }

    @PutMapping("/events/visit/{id}")
    public EventDto visitEvent(@PathVariable(name = "id") long eventId) {
        User user = userService.getRequester();
        return service.visitEvent(user, eventId);
    }

    @PostMapping("/events/add")
    public EventDto createNewEvent(@RequestBody Event event) {
        return service.save(event);
    }

    @PutMapping(path = "/events/update/{id}")
    public EventDto updateEvent(@PathVariable("id") long eventId, @RequestBody Event newEvent) {
        return service.update(eventId, newEvent);
    }

    @DeleteMapping(path = "/events/delete/{id}")
    public void deleteEvent(@PathVariable("id") long id) {
        service.delete(id);
    }

}