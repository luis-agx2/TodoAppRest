package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.MyUserDetails;
import com.lag.todoapp.rest.todoapprest.dto.TaskDashboardDto;
import com.lag.todoapp.rest.todoapprest.dto.TaskDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.CategoryEntity;
import com.lag.todoapp.rest.todoapprest.entity.TaskEntity;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.enums.TaskStatusEnum;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import com.lag.todoapp.rest.todoapprest.mapper.TaskMapper;
import com.lag.todoapp.rest.todoapprest.repository.CategoryRepository;
import com.lag.todoapp.rest.todoapprest.repository.TaskRepository;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<TaskDto> getAllMe(Integer page, Integer size) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pageable pageable = PageRequest.of(page, size);
        Page<TaskEntity> taskResult = taskRepository.findAllByUserId(userDetails.getId(), pageable);
        List<TaskDto> tasksDto = taskMapper.toListCategoryDto(taskResult.getContent());

        return new PageImpl<>(tasksDto, taskResult.getPageable(), taskResult.getTotalElements());
    }

    @Override
    public TaskDashboardDto getAllMeDashboard() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return TaskDashboardDto.builder()
                .news(taskMapper.toListCategoryDto(taskRepository.findAllByStatusAndUserId(TaskStatusEnum.NEW, userDetails.getId())))
                .inProgress(taskMapper.toListCategoryDto(taskRepository.findAllByStatusAndUserId(TaskStatusEnum.IN_PROGRESS, userDetails.getId())))
                .paused(taskMapper.toListCategoryDto(taskRepository.findAllByStatusAndUserId(TaskStatusEnum.PAUSE, userDetails.getId())))
                .cancelled(taskMapper.toListCategoryDto(taskRepository.findAllByStatusAndUserId(TaskStatusEnum.CANCELED, userDetails.getId())))
                .completed(taskMapper.toListCategoryDto(taskRepository.findAllByStatusAndUserId(TaskStatusEnum.COMPLETED, userDetails.getId())))
                .build();
    }

    @Override
    public TaskDto getOneMe(Long id)throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow();

        if (!userDetails.getId().equals(taskEntity.getUser().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        return taskMapper.toDto(taskEntity);
    }

    @Override
    public TaskDto createMe(TaskEntradaDto taskEntradaDto) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity userEntity = userRepository.findById(userDetails.getId()).orElseThrow();
        CategoryEntity categoryEntity = categoryRepository.findById(taskEntradaDto.getCategoryId()).orElseThrow(() -> new AccessNotGrantedException("Forbidden"));

        TaskEntity tasktoCreate = taskMapper.toEntity(taskEntradaDto);
        tasktoCreate.setUser(userEntity);
        tasktoCreate.setCategory(categoryEntity);
        tasktoCreate.setStatus(TaskStatusEnum.NEW);
        tasktoCreate.setCreatedAt(LocalDateTime.now());

        return taskMapper.toDto(taskRepository.save(tasktoCreate));
    }

    @Override
    public TaskDto updateMe(Long taskId, TaskUpdateDto taskUpdateDto) throws AccessNotGrantedException, OptionNotFoundException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new AccessNotGrantedException("Forbidden"));

        if (!userDetails.getId().equals(taskEntity.getUser().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        TaskEntity taskToEdit = taskMapper.toEntytiForEdit(taskEntity, taskUpdateDto);

        return taskMapper.toDto(taskRepository.save(taskToEdit));
    }

    @Override
    public void deleteMe(Long taskId) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new AccessNotGrantedException("Forbidden"));

        if (!userDetails.getId().equals(taskEntity.getUser().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        taskRepository.deleteById(taskId);
    }
}
