package com.radius.marketplace.service;

import com.radius.marketplace.constants.RedisKeys;
import com.radius.marketplace.model.Property;
import com.radius.marketplace.model.PropertySearchResponse;
import com.radius.marketplace.model.Requirement;
import com.radius.marketplace.model.RequirementSearchResponse;
import com.radius.marketplace.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SearchService {
    public static final RedisGeoCommands.GeoRadiusCommandArgs GEO_ARGS = RedisGeoCommands.GeoRadiusCommandArgs
            .newGeoRadiusArgs()
            .includeDistance()
            .sortAscending();
    private final GeoOperations<String, String> geoOperations;
    private final PropertyService propertyService;
    private final RequirementService requirementService;

    @Autowired
    public SearchService(StringRedisTemplate stringRedisTemplate,
                         PropertyService propertyService,
                         RequirementService requirementService) {
        this.geoOperations = stringRedisTemplate.opsForGeo();
        this.propertyService = propertyService;
        this.requirementService = requirementService;
    }

    public List<PropertySearchResponse> searchPropertiesByRequirementId(String requirementId) {
        return requirementService.findById(requirementId)
                .map(this::searchPropertiesByRequirement)
                .orElse(Collections.emptyList());
    }

    public List<PropertySearchResponse> searchPropertiesByRequirement(Requirement requirement) {
        Circle searchCircle = SearchUtil.createSearchCircle(requirement.getLatitude(), requirement.getLongitude());
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.radius(RedisKeys.PROPERTIES.getKey(),
                searchCircle, GEO_ARGS);
        if (null == results) {
            return Collections.emptyList();
        }
        return results.getContent().stream()
                .map(this::createPropertySearchResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // todo:
    }

    private PropertySearchResponse createPropertySearchResponse(GeoResult<RedisGeoCommands.GeoLocation<String>> result) {
        return propertyService.findById(result.getContent().getName())
                .map(a -> PropertySearchResponse.builder()
                        .property(a)
                        .distance(result.getDistance().getValue())
                        .build())
                .orElse(null);
    }

    public List<RequirementSearchResponse> searchRequirementsByPropertyId(String propertyId) {
        return propertyService.findById(propertyId)
                .map(this::searchRequirementsByProperty)
                .orElse(Collections.emptyList());
    }

    public List<RequirementSearchResponse> searchRequirementsByProperty(Property property) {
        Circle searchCircle = SearchUtil.createSearchCircle(property.getLatitude(), property.getLongitude());
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.radius(RedisKeys.REQUIREMENTS.getKey(),
                searchCircle, GEO_ARGS);
        if (null == results) {
            return Collections.emptyList();
        }
        return results.getContent().stream()
                .map(this::createRequirementSearchResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // todo:
    }

    private RequirementSearchResponse createRequirementSearchResponse(GeoResult<RedisGeoCommands.GeoLocation<String>> result) {
        return requirementService.findById(result.getContent().getName())
                .map(a -> RequirementSearchResponse.builder()
                        .requirement(a)
                        .distance(result.getDistance().getValue())
                        .build())
                .orElse(null);
    }
}
