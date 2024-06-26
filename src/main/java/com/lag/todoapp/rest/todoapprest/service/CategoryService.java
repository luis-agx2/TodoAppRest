package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategoriesByUserId(Long id);
}
