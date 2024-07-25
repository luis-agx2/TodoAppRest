package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.TaskDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.TaskEntity;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {
    final CategoryMapper categoryMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    public TaskMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new PropertyMap<TaskEntradaDto, TaskEntity>() {
            protected void configure() {
                skip().setId(0L);
            }
        });
    }

    public List<TaskDto> toListCategoryDto(List<TaskEntity> taskEntities) {
        return taskEntities.stream()
                .map(this::toDto)
                .toList();
    }

    public TaskDto toDto(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDto.class);
    }

    public TaskEntity toEntity(TaskEntradaDto taskEntradaDto) {
        return modelMapper.map(taskEntradaDto, TaskEntity.class);
    }

    public TaskEntity toEntity(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskEntity.class);
    }

    public TaskEntity toEntytiForEdit(TaskEntity taskEntity, TaskUpdateDto taskUpdateDto) throws OptionNotFoundException {
        TaskEntity taskToEdit = toEntity(taskEntity);

        try {
            modelMapper.map(taskUpdateDto, taskToEdit);
        } catch (Exception e) {
            throw new OptionNotFoundException("Not found some option of your preferences");
        }
        return taskToEdit;
    }
}
