package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.CommentDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentUpdateDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskEntradaDto;
import com.lag.todoapp.rest.todoapprest.entity.CommentEntity;
import com.lag.todoapp.rest.todoapprest.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    TypeMap<CommentEntity, CommentDto> mapperCommentToDto = modelMapper.createTypeMap(CommentEntity.class, CommentDto.class);
    TypeMap<CommentEntity, CommentEntity> mapperEntity = modelMapper.createTypeMap(CommentEntity.class, CommentEntity.class);

    public CommentMapper() {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public List<CommentDto> toListDto(List<CommentEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::entityToDto)
                .toList();
    }

    public CommentDto entityToDto(CommentEntity commentEntity) {
        mapperCommentToDto.addMapping(CommentEntity::getId, CommentDto::setId);
        mapperCommentToDto.addMapping(CommentEntity::getMessage, CommentDto::setMessage);
        mapperCommentToDto.addMapping(CommentEntity::getTitle, CommentDto::setTitle);

        return mapperCommentToDto.map(commentEntity);
    }

    public CommentEntity entradaDtoToEntity(CommentEntradaDto commentEntradaDto) {
        return modelMapper.map(commentEntradaDto, CommentEntity.class);
    }

    public CommentEntity toEntityForUpdate(CommentEntity commentEntity, CommentUpdateDto commentUpdateDto) {
        CommentEntity commentEntityToEdit = toEntity(commentEntity);

        modelMapper.map(commentUpdateDto, commentEntityToEdit);

        return commentEntityToEdit;
    }

    public CommentEntity toEntity(CommentEntity commentEntity) {
        return mapperEntity.map(commentEntity);
    }
}
