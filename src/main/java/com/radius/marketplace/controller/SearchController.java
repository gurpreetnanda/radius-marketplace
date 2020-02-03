package com.radius.marketplace.controller;

import com.radius.marketplace.model.PropertySearchResponse;
import com.radius.marketplace.model.RequirementSearchResponse;
import com.radius.marketplace.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("requirements")
    public List<RequirementSearchResponse> searchRequirements(String propertyId) {
        return searchService.searchRequirementsByPropertyId(propertyId);
    }

    @GetMapping("properties")
    public List<PropertySearchResponse> searchProperties(String requirementId) {
        return searchService.searchPropertiesByRequirementId(requirementId);
    }

}
