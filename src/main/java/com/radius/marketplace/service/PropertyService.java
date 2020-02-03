package com.radius.marketplace.service;

import com.google.common.collect.Lists;
import com.radius.marketplace.constants.RedisKeys;
import com.radius.marketplace.model.Property;
import com.radius.marketplace.repository.PropertyRepository;
import com.radius.marketplace.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    private PropertyRepository repository;
    private GeoOperations<String, String> geoOperations;

    @Autowired
    public PropertyService(PropertyRepository repository, StringRedisTemplate stringRedisTemplate) {
        this.repository = repository;
        this.geoOperations = stringRedisTemplate.opsForGeo();
    }

    public Optional<Property> findById(String id) {
        return repository.findById(id);
    }

    public List<Property> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    public Property save(Property property) {
        Property save = repository.save(property);
        Point point = SearchUtil.createPoint(property.getLatitude(), property.getLongitude());
        geoOperations.add(RedisKeys.PROPERTIES.getKey(), point, String.valueOf(save.getId()));
        return save;
    }
}
