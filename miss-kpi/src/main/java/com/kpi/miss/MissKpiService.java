package com.kpi.miss;

import com.kpi.miss.repository.MissKpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissKpiService {

    @Autowired
    private MissKpiRepository missKpiRepository;


}
