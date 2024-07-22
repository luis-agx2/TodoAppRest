package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;
import com.lag.todoapp.rest.todoapprest.dto.MyUserDetails;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.CategoryEntity;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.mapper.CategoryMapper;
import com.lag.todoapp.rest.todoapprest.repository.CategoryRepository;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getAllMe() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<CategoryEntity> categoryEntities = categoryRepository.findAllByUserId(userDetails.getId());

        return categoryMapper.toListCategoryDto(categoryEntities);
    }

    @Transactional
    @Override
    public CategoryDto createMe(CategoryEntradaDto categoryEntradaDto) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CategoryEntity categoryToSave = categoryMapper.toCategoryEntity(categoryEntradaDto);
        categoryToSave.setUser(user);
        categoryToSave.setCreatedAt(LocalDateTime.now());

        return categoryMapper.toCategoryDto(categoryRepository.save(categoryToSave));
    }

    @Transactional
    @Override
    public CategoryDto updateMe(CategoryUpdateDto categoryUpdateDto, Long categoryId) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow();

        if (!userDetails.getId().equals(categoryEntity.getUser().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        CategoryEntity categoryToEdit = categoryMapper.toCategoryEntityForUpdate(categoryEntity, categoryUpdateDto);

        return categoryMapper.toCategoryDto(categoryRepository.save(categoryToEdit));
    }

    @Transactional
    @Override
    public void deleteMe(Long categoryId) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow();

        if (!userDetails.getId().equals(categoryEntity.getUser().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        categoryRepository.deleteById(categoryId);
    }
}
