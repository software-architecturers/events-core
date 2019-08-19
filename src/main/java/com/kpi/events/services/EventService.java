package com.kpi.events.services;

import com.kpi.events.config.HibernateUtil;
import com.kpi.events.model.Event;
import com.kpi.events.model.Image;
import com.kpi.events.model.User;
import com.kpi.events.model.dto.EventDto;
import com.kpi.events.model.dto.EventVisitorsDto;
import com.kpi.events.model.dto.LocationDto;
import com.kpi.events.model.dto.UserVisitorEventDto;
import com.kpi.events.model.repository.EventRepository;
import com.kpi.events.model.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kpi.events.utils.Constants.WRONG_INDEX;

@Service
public class EventService /*implements IService<Event>*/ {
    public static final int VISITORS_SHOWN_LIMIT = 3;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    //    @Override
    public List<EventDto> findAll(int size, int page) {
        User userRequester = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User persistedUser = userRepository.findByLogin(userRequester.getLogin());

        return eventRepository.findAll(PageRequest.of(page, size)).getContent()
                .stream()
                .map(event -> convertToDto(event, persistedUser))
                .collect(Collectors.toList());
    }

    //    @Override
    @Transactional
    public EventDto save(Event entity) {
        List<Image> eventImages = entity.getImages().stream()
                .map(image -> imageService.find(image.getId()))
                .collect(Collectors.toList());
        entity.setImages(new HashSet<>(eventImages));
        entity.setCreationDate(LocalDateTime.now());

        User creator = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User persistedUser = userRepository.findByLogin(creator.getLogin());
        entity.setCreator(persistedUser);
        return convertToDto(eventRepository.save(entity), persistedUser);

    }

    //    @Override
    public Event find(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format(WRONG_INDEX, id)));
    }

    //    @Override
    public Event update(long id, Event entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        entity.setId(id);
        return entity;
    }

    //    @Override
    public void delete(long id) {
        eventRepository.deleteById(id);
    }

    public List<EventDto> searchEventLIKEGOOGLE(String searchWord) {
        User userRequester = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User persistedUser = userRepository.findByLogin(userRequester.getLogin());
        return eventRepository.findEventByDescriptionContainingOrTitleContaining(searchWord, searchWord)
                .stream()
                .map(event -> convertToDto(event, persistedUser))
                .collect(Collectors.toList());
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
                .description(event.getDescription())
                .imagesLinks(event.getImages()
                        .stream()
                        .map(Image::getLink)
                        .collect(Collectors.toList()))
                .title(event.getTitle())
                .likes(event.getLikes().size())
                .locationDto(event.getLocation())
                .isLiked(checkIfLiked(event, user))
                .creator(UserVisitorEventDto.builder()
                        .login(user.getLogin())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .secondName(user.getSecondName())
                        .build())
                .visitors(new EventVisitorsDto(event.getVisitors().stream().limit(VISITORS_SHOWN_LIMIT).map(visitor ->
                        UserVisitorEventDto.builder()
                                .login(visitor.getLogin())
                                .email(visitor.getEmail())
                                .firstName(visitor.getFirstName())
                                .secondName(visitor.getSecondName())
                                .build())
                        .collect(Collectors.toSet()),
                        event.getVisitors().size()))
                .build();
    }

    private boolean checkIfLiked(Event event, User user) {
        return event.getLikes().contains(user);
    }

    @Transactional
    public EventDto visitEvent(User user, long eventId) {
        Event eventToVisit = eventRepository.findById(eventId)
                .orElseThrow(RuntimeException::new);
        User persistedUser = userRepository.findByLogin(user.getLogin());
        setOrDeleteVisit(eventToVisit, persistedUser);
        return convertToDto(eventToVisit, persistedUser);
    }

    private void setOrDeleteVisit(Event eventToVisit, User persistedUser) {
        Set<User> visitors = eventToVisit.getVisitors();
        if (visitors.contains(persistedUser)) {
            visitors.remove(persistedUser);
        } else {
            visitors.add(persistedUser);
        }
    }
}
