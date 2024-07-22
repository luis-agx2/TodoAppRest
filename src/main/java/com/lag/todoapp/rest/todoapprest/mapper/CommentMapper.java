package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.CommentDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    TypeMap<CommentEntity, CommentDto> mapperCommentToDto = modelMapper.createTypeMap(CommentEntity.class, CommentDto.class);
    TypeMap<CommentEntradaDto, CommentEntity> mapperEntradaToEntity = modelMapper.createTypeMap(CommentEntradaDto.class, CommentEntity.class);
    TypeMap<CommentEntity, CommentEntity> mapperEntity = modelMapper.createTypeMap(CommentEntity.class, CommentEntity.class);

    public CommentMapper() {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
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
        mapperEntradaToEntity.addMapping(CommentEntradaDto::getTitle, CommentEntity::setTitle);
        mapperEntradaToEntity.addMapping(CommentEntradaDto::getMessage, CommentEntity::setMessage);

        return mapperEntradaToEntity.map(commentEntradaDto);
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
