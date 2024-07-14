package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    TypeMap<CategoryEntity, CategoryDto> mapperCategoryToDTO = modelMapper.createTypeMap(CategoryEntity.class, CategoryDto.class);
    TypeMap<CategoryEntradaDto, CategoryEntity> mapperEntradaDtoToEntity = modelMapper.createTypeMap(CategoryEntradaDto.class, CategoryEntity.class);
    TypeMap<CategoryEntity, CategoryEntity> mapperEntityToEntity = modelMapper.createTypeMap(CategoryEntity.class, CategoryEntity.class);

    public CategoryMapper() {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public List<CategoryDto> toListCategoryDto(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::toCategoryDto)
                .toList();
    }

    public CategoryDto toCategoryDto(CategoryEntity categoryEntity) {
        mapperCategoryToDTO.addMapping(CategoryEntity::getId, CategoryDto::setId);
        mapperCategoryToDTO.addMapping(CategoryEntity::getName, CategoryDto::setName);
        mapperCategoryToDTO.addMapping(CategoryEntity::getDescription, CategoryDto::setDescription);
        mapperCategoryToDTO.addMapping(CategoryEntity::getColor, CategoryDto::setColor);

        return mapperCategoryToDTO.map(categoryEntity);
    }

    public CategoryEntity toCategoryEntity(CategoryEntradaDto categoryEntradaDto) {
        mapperEntradaDtoToEntity.addMapping(CategoryEntradaDto::getName, CategoryEntity::setName);
        mapperEntradaDtoToEntity.addMapping(CategoryEntradaDto::getDescription, CategoryEntity::setDescription);
        mapperEntradaDtoToEntity.addMapping(CategoryEntradaDto::getColor, CategoryEntity::setColor);

        return mapperEntradaDtoToEntity.map(categoryEntradaDto);
    }

    public CategoryEntity toCategoryEntityForUpdate(CategoryEntity categoryEntity, CategoryUpdateDto categoryUpdateDto) {
        CategoryEntity categoryEntityToEdit = toCategoryEntity(categoryEntity);

        modelMapper.map(categoryUpdateDto, categoryEntityToEdit);

        return categoryEntityToEdit;
    }

    public CategoryEntity toCategoryEntity(CategoryEntity categoryEntity) {
        return mapperEntityToEntity.map(categoryEntity);
    }
}
