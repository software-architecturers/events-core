package com.kpi.miss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@ConditionalOnProperty(name = "features.events")
public class MissKpiController {

    @Autowired
    private MissKpiService service;

    @GetMapping(path = "/miss_kpi")
    public String hello() {
        return "Hello world";
    }
}