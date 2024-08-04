package com.lag.todoapp.rest.todoapprest.repository;

import com.lag.todoapp.rest.todoapprest.entity.TaskEntity;
import com.lag.todoapp.rest.todoapprest.enums.TaskStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findAllByUserId(Long id, Pageable pageable);

    List<TaskEntity> findAllByStatusAndUserId(TaskStatusEnum status, Long userId);
}
