package com.kpi.events.model.repository;

import com.kpi.events.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

//    @Query("select i.links from images i where id=:id")
//    List<String> find(@Param("id") long id);
}
