package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;
import com.lag.todoapp.rest.todoapprest.entity.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    private ModelMapper modelMapper = new ModelMapper();
    TypeMap<CategoryEntity, CategoryDto> mapperCategoryToDTO = modelMapper.createTypeMap(CategoryEntity.class, CategoryDto.class);


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
}
