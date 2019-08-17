package com.kpi.events.controllers;

import com.kpi.events.services.FeaturesStatusService;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api")
public class FeatureController {

    @Autowired
    private FeaturesStatusService service;

    @GetMapping(path = "/features")
    public Object getActiveFeatures() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getAllFeaturesStatus();
    }
}
