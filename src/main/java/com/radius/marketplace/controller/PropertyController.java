package com.radius.marketplace.controller;

import com.radius.marketplace.model.Property;
import com.radius.marketplace.model.RequirementSearchResponse;
import com.radius.marketplace.service.PropertyService;
import com.radius.marketplace.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("property")
public class PropertyController {
    private final PropertyService propertyService;
    private final SearchService searchService;

    @Autowired
    public PropertyController(PropertyService propertyService, SearchService searchService) {
        this.propertyService = propertyService;
        this.searchService = searchService;
    }

    @GetMapping
    public Property getProperty(String id) {
        return propertyService.findById(id).orElse(null);
    }

    @GetMapping("all")
    public List<Property> getAllProperties() {
        return propertyService.findAll();
    }

    @PostMapping
    public List<RequirementSearchResponse> save(@RequestBody Property property) {
        propertyService.save(property);
        return searchService.searchRequirementsByProperty(property);
    }
}
