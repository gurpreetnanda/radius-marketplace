package com.radius.marketplace.repository;

import com.radius.marketplace.model.Requirement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends CrudRepository<Requirement, String> {
}
