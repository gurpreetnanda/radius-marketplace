package com.radius.marketplace.repository;

import com.radius.marketplace.model.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<Property, String> {
}
