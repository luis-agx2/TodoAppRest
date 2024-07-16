package com.lag.todoapp.rest.todoapprest.repository;

import com.lag.todoapp.rest.todoapprest.entity.PreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<PreferenceEntity, Long> {
}
