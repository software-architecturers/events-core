package com.kpi.events.services;

import com.kpi.events.config.HibernateUtil;
import com.kpi.events.model.Event;
import com.kpi.events.model.Image;
import com.kpi.events.model.User;
import com.kpi.events.model.dto.EventDto;
import com.kpi.events.model.dto.LocationDto;
import com.kpi.events.model.repository.EventRepository;
import com.kpi.events.model.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.kpi.events.utils.Constants.WRONG_INDEX;

@Service
public class EventService implements IService<Event> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

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

    public List<Event> findEventsByLocation(LocationDto leftBotPoint, LocationDto rightTopPoint) {
        return eventRepository
                .findAllByLocation(
                        leftBotPoint.getLatitude(), rightTopPoint.getLatitude(),
                        leftBotPoint.getLongitude(), rightTopPoint.getLongitude());
    }

    @Transactional
    public EventDto likeEvent(User user, long eventId) {
        Event eventToLike = eventRepository.findById(eventId)
                .orElseThrow(RuntimeException::new);
        User persistedUser = userRepository.findByLogin(user.getLogin());
        setOrDeleteLike(eventToLike, persistedUser);
        return convertToDto(eventToLike, persistedUser);
    }

    private void setOrDeleteLike(Event eventToLike, User persistedUser) {
        Set<User> likes = eventToLike.getLikes();
        if (likes.contains(persistedUser)) {
            likes.remove(persistedUser);
        } else {
            likes.add(persistedUser);
        }
    }

    private EventDto convertToDto(Event event, User user) {
        return EventDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .imagesLinks(event.getImages().stream().map(Image::getLink).collect(Collectors.toList()))
                .title(event.getTitle())
                .likes(event.getLikes().size())
                .locationDto(event.getLocation())
                .isLiked(checkIfLiked(event, user))
                .build();
    }

    private boolean checkIfLiked(Event event, User user) {
        return event.getLikes().contains(user);
    }
}
