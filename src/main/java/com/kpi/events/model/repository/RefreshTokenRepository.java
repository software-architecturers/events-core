package com.kpi.events.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpi.events.model.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
