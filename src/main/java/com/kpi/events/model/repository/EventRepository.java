package com.kpi.events.model.repository;

import com.kpi.events.model.Event;

import com.kpi.events.model.dto.LocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventByDescriptionContainingOrTitleContaining(String search, String searchInTitle);

    @Query(value = "select * from events_table where latitude between ?1 AND ?2  AND longitude between  ?3 AND ?4",
            nativeQuery = true)
    List<Event> findAllByLocation(BigDecimal leftLatitude, BigDecimal rightLatitude,
                                  BigDecimal leftLongtitude , BigDecimal rightLongtitude );


}