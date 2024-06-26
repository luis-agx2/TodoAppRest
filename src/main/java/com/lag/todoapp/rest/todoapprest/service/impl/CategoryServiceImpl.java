package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;
import com.lag.todoapp.rest.todoapprest.entity.CategoryEntity;
import com.lag.todoapp.rest.todoapprest.mapper.CategoryMapper;
import com.lag.todoapp.rest.todoapprest.repository.CategoryRepository;
import com.lag.todoapp.rest.todoapprest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getAllCategoriesByUserId(Long id) {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllByUserId(id);

        return categoryMapper.toListCategoryDto(categoryEntities);
    }
}
