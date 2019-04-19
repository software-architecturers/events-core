package com.kpi.events.model.repository;

import com.kpi.events.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventByDescriptionContainingOrTitleContaining(String search, String searchInTitle);

}
