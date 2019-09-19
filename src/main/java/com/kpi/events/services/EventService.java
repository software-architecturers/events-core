package com.kpi.events.services;

import com.kpi.events.config.HibernateUtil;
import com.kpi.events.exceptions.UserNotFoundException;
import com.kpi.events.model.Event;
import com.kpi.events.model.Image;
import com.kpi.events.model.User;
import com.kpi.events.model.dtos.event.EventDto;
import com.kpi.events.model.dtos.location.LocationDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import com.kpi.events.model.mapper.EventMapper;
import com.kpi.events.model.mapper.UserMapper;
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

import static com.kpi.events.utils.Constants.WRONG_INDEX;

@Service
public class EventService /* implements IService<EventDto>*/ {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserMapper userMapper;

    //    @Override
    @Transactional
    public List<EventDto> findAll(int size, int page) {
        User userRequester = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User persistedUser = userRepository.findByLogin(userRequester.getLogin()).orElseThrow(UserNotFoundException::new);

        return eventRepository.findAll(PageRequest.of(page, size)).getContent()
                .stream()
                .map(event -> eventMapper.convertToDto(event, persistedUser))
                .collect(Collectors.toList());
    }

    @Transactional
    public EventDto save(Event entity) {
        List<Image> eventImages = entity.getImages().stream()
                .map(image -> imageService.find(image.getId()))
                .collect(Collectors.toList());
        entity.setImages(new HashSet<>(eventImages));
        entity.setCreationDate(LocalDateTime.now());

        User creator = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User persistedUser = userRepository.findByLogin(creator.getLogin()).orElseThrow(UserNotFoundException::new);
        entity.setCreator(persistedUser);
        return eventMapper.convertToDto(eventRepository.save(entity), persistedUser);

    }

    @Transactional
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
    @Transactional
    public void delete(long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public List<EventDto> searchEventLIKEGOOGLE(String searchWord, int limit, int page) {
        User userRequester = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User persistedUser = userRepository.findByLogin(userRequester.getLogin()).orElseThrow(UserNotFoundException::new);
        return eventRepository.searchEvents(searchWord.toUpperCase(), PageRequest.of(page, limit))
                .stream()
                .map(event -> eventMapper.convertToDto(event, persistedUser))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<EventDto> findEventsByLocation(LocationDto leftBotPoint, LocationDto rightTopPoint) {
        User userRequester = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User persistedUser = userRepository.findByLogin(userRequester.getLogin()).orElseThrow(UserNotFoundException::new);
        return eventRepository
                .findAllByLocation(
                        leftBotPoint.getLatitude(), rightTopPoint.getLatitude(),
                        leftBotPoint.getLongitude(), rightTopPoint.getLongitude())
                .stream().map(event-> eventMapper.convertToDto(event, persistedUser)).collect(Collectors.toList());
    }

    @Transactional
    public EventDto likeEvent(User user, long eventId) {
        Event eventToLike = eventRepository.findById(eventId)
                .orElseThrow(RuntimeException::new);
        User persistedUser = userRepository.findByLogin(user.getLogin()).orElseThrow(UserNotFoundException::new);
        setOrDeleteLike(eventToLike, persistedUser);
        return eventMapper.convertToDto(eventToLike, persistedUser);
    }

    @Transactional
    public EventDto visitEvent(User user, long eventId) {
        Event eventToVisit = eventRepository.findById(eventId)
                .orElseThrow(RuntimeException::new);
        User persistedUser = userRepository.findByLogin(user.getLogin()).orElseThrow(UserNotFoundException::new);
        setOrDeleteVisit(eventToVisit, persistedUser);
        return eventMapper.convertToDto(eventToVisit, persistedUser);
    }

    private void setOrDeleteLike(Event eventToLike, User persistedUser) {
        Set<User> likes = eventToLike.getLikes();
        if (likes.contains(persistedUser)) {
            likes.remove(persistedUser);
        } else {
            likes.add(persistedUser);
        }
    }

    private void setOrDeleteVisit(Event eventToVisit, User persistedUser) {
        Set<User> visitors = eventToVisit.getVisitors();
        if (visitors.contains(persistedUser)) {
            visitors.remove(persistedUser);
        } else {
            visitors.add(persistedUser);
        }
    }

    public List<SmallUserDto> getEventVisitors(long eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(RuntimeException::new).getVisitors()
                .stream().map(user -> userMapper.convertToRegisteredDto(user))
                .collect(Collectors.toList());
    }
}
