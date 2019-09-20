package com.kpi.events.model.repository;

import com.kpi.events.model.Event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "from Event where upper(title) like %:search% or upper(description) like %:search%")
    List<Event> searchEvents(String search, Pageable pageable);

    @Query(value = "select * from events_table where latitude between ?1 and ?2 and longitude between ?3 and ?4 and start_date > current_timestamp order by start_date",
            nativeQuery = true)
    List<Event> findAllByLocation(BigDecimal leftLatitude, BigDecimal rightLatitude,
                                  BigDecimal leftLongitude, BigDecimal rightLongitude);

}