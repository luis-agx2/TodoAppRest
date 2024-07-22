package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllMe();

    CategoryDto createMe(CategoryEntradaDto categoryEntradaDto);

    CategoryDto updateMe(CategoryUpdateDto categoryUpdateDto, Long categoryId) throws AccessNotGrantedException;

    void deleteMe(Long id) throws AccessNotGrantedException;
}
