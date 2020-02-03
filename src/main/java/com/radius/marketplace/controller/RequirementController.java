package com.radius.marketplace.controller;

import com.radius.marketplace.model.PropertySearchResponse;
import com.radius.marketplace.model.Requirement;
import com.radius.marketplace.service.RequirementService;
import com.radius.marketplace.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("requirement")
public class RequirementController {
    private RequirementService requirementService;
    private SearchService searchService;

    @Autowired
    public RequirementController(RequirementService requirementService, SearchService searchService) {
        this.requirementService = requirementService;
        this.searchService = searchService;
    }

    @GetMapping
    public Requirement getProperty(String id) {
        return requirementService.findById(id).orElse(null);
    }

    @GetMapping("all")
    public List<Requirement> getAllProperties() {
        return requirementService.findAll();
    }

    @PostMapping
    public List<PropertySearchResponse> save(@RequestBody Requirement requirement) {
        requirementService.save(requirement);
        return searchService.searchByRequirement(requirement);
    }
}
