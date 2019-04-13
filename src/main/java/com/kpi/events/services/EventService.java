package com.kpi.events.services;

import com.kpi.events.model.Event;
import com.kpi.events.model.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kpi.events.utils.Constants.WRONG_INDEX;

@Service
public class EventService implements IService<Event> {

    @Autowired
    private EventRepository repository;

    @Override
    public List<Event> findAll(int size, int page) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public Event save(Event entity) {
        return repository.save(entity);
    }

    @Override
    public Event find(long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format(WRONG_INDEX, id)));
    }

    @Override
    public Event update(long id, Event entity) {
        Event event = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format(WRONG_INDEX, id)));
        event = entity;
        return repository.save(event);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<Event> searchEventLIKEGOOGLE(String searchWord) {
        return repository.findEventByDescriptionContainingOrTitleContaining(searchWord, searchWord);
    }
}
