package com.kpi.events.services;

import com.kpi.events.config.HibernateUtil;
import com.kpi.events.model.Event;
import com.kpi.events.model.Image;
import com.kpi.events.model.repository.EventRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kpi.events.utils.Constants.WRONG_INDEX;

@Service
public class EventService implements IService<Event> {

    @Autowired
    @Qualifier("eventRepository")
    private EventRepository eventRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public List<Event> findAll(int size, int page) {
        return eventRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public Event save(Event entity) {
        List<Image> eventImages = entity.getImages().stream()
                .map(image -> imageService.find(image.getId()))
                .collect(Collectors.toList());
        entity.setImages(new HashSet<>(eventImages));
        entity.setCreationDate(LocalDateTime.now());
        return eventRepository.save(entity);
    }

    @Override
    public Event find(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format(WRONG_INDEX, id)));
    }

    @Override
    public Event update(long id, Event entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        entity.setId(id);
        return entity;
    }

    @Override
    public void delete(long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> searchEventLIKEGOOGLE(String searchWord) {
        return eventRepository.findEventByDescriptionContainingOrTitleContaining(searchWord, searchWord);
    }

    public List<String> findImageLinks(long id) {
        return null;
    }

}
