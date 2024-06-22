package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.entity.RoleEntity;

import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> findById(Long id);
}
