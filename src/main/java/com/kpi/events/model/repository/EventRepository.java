package com.kpi.events.model.repository;

import com.kpi.events.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("eventRepository")
public interface EventRepository extends JpaRepository<Event, Long> {

//    @Query("update event set where event.id=:id")
//    void updateEventById(Event event, long id);

    List<Event> findEventByDescriptionContainingOrTitleContaining(String search, String searchInTitle);


    @Query("select i.links from images i where id=:id")
    List<String> findImageLinks(@Param("id") long id);
}
