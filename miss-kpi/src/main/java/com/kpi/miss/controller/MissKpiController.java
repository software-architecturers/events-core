package com.kpi.miss.controller;

import com.kpi.miss.model.MissKpiEntity;
import com.kpi.miss.service.MissKpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/miss_kpi")
@ConditionalOnProperty(name = "features.events.miss")
public class MissKpiController {

    @Autowired
    private MissKpiService service;

    @GetMapping("/participants")
    public List<MissKpiEntity> getParticipants(@RequestParam(required = false, defaultValue = "10") int limit,
                                               @RequestParam(required = false, defaultValue = "0") int page) {
        return service.findAll(limit, page);
    }


    @PostMapping("/participants/like/{id}")
    public MissKpiEntity likeParticipant(@PathVariable long id) {
        return service.likeParticipant(id);
    }


    @PostMapping("/participants/unlike/{id}")
    public MissKpiEntity unlikeParticipant(@PathVariable long id) {
        return service.unlikeParticipant(id);
    }
}