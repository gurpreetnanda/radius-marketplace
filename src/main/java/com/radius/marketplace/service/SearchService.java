package com.radius.marketplace.service;

import com.radius.marketplace.constants.RedisKeys;
import com.radius.marketplace.model.Property;
import com.radius.marketplace.model.PropertySearchResponse;
import com.radius.marketplace.model.Requirement;
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
    // todo: configurable
    public static final int RADIUS = 10;
    public static final RedisGeoCommands.GeoRadiusCommandArgs GEO_ARGS = RedisGeoCommands.GeoRadiusCommandArgs
            .newGeoRadiusArgs()
            .includeDistance()
            .sortAscending();
    private final GeoOperations<String, String> geoOperations;
    private final PropertyService propertyService;

    @Autowired
    public SearchService(StringRedisTemplate stringRedisTemplate, PropertyService propertyService) {
        this.geoOperations = stringRedisTemplate.opsForGeo();
        this.propertyService = propertyService;
    }

    public List<Property> searchByRequirementId(int requirementId) {
        // todo:
        return Collections.emptyList();
    }

    public List<Requirement> searchByPropertyId(int propertyId) {
        // todo:
        return Collections.emptyList();
    }

    public List<PropertySearchResponse> searchByRequirement(Requirement requirement) {
        Circle searchCircle = SearchUtil.createCircle(requirement.getLatitude(), requirement.getLongitude(), RADIUS);
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

    public List<Requirement> searchByProperty(Property property) {
        // todo:
        return Collections.emptyList();
    }
}
