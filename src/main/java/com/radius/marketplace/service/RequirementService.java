package com.radius.marketplace.service;

import com.google.common.collect.Lists;
import com.radius.marketplace.constants.RedisKeys;
import com.radius.marketplace.model.Requirement;
import com.radius.marketplace.repository.RequirementRepository;
import com.radius.marketplace.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequirementService {
    private final RequirementRepository repository;
    private final GeoOperations<String, String> geoOperations;

    @Autowired
    public RequirementService(RequirementRepository repository, StringRedisTemplate stringRedisTemplate) {
        this.repository = repository;
        this.geoOperations = stringRedisTemplate.opsForGeo();
    }

    public Optional<Requirement> findById(String id) {
        return repository.findById(id);
    }

    public List<Requirement> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    public Requirement save(Requirement requirement) {
        Requirement save = repository.save(requirement);
        Point point = SearchUtil.createPoint(requirement.getLatitude(), requirement.getLongitude());
        geoOperations.add(RedisKeys.REQUIREMENTS.getKey(), point, String.valueOf(save.getId()));
        return save;
    }
}
