package com.lag.todoapp.rest.todoapprest.repository;

import com.lag.todoapp.rest.todoapprest.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByUserId(Long id);
}
