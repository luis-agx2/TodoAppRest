package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.TaskDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<TaskDto> getAllMe(Integer page, Integer size);

    TaskDto getOneMe(Long id) throws AccessNotGrantedException;

    TaskDto createMe(TaskEntradaDto taskEntradaDto) throws AccessNotGrantedException;

    TaskDto updateMe(Long taskId, TaskUpdateDto taskUpdateDto) throws AccessNotGrantedException, OptionNotFoundException;

    void deleteMe(Long taskId) throws AccessNotGrantedException;
}
