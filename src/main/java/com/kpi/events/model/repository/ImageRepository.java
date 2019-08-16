package com.kpi.events.model.repository;

import com.kpi.events.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository <Image, Long> {

}
