package com.kpi.miss.repository;

import com.kpi.miss.MissKpiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MissKpiRepository extends JpaRepository<MissKpiEntity, Long> {


}
