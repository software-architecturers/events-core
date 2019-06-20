package com.kpi.events.controllers;

import com.kpi.events.model.Hostel;
import com.kpi.events.services.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HostelController {

    @Autowired
    private HostelService service;

    @RequestMapping("/hostelInfo/{id}")
    public Hostel getHostel(@PathVariable("id") long studentId) {
        return service.findByStudentId(studentId);
    }

}