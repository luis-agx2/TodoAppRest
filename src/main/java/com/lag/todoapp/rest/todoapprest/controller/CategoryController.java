package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CategoryUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<CategoryDto>> getAllByUserId() {
        return ResponseEntity.ok(categoryService.getAllMe());
    }

    @PostMapping("/me")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryEntradaDto category) {
        return ResponseEntity.ok(categoryService.createMe(category));
    }

    @PutMapping("/me/{categoryId}")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryUpdateDto category, @PathVariable Long categoryId) throws AccessNotGrantedException {
        return ResponseEntity.ok(categoryService.updateMe(category, categoryId));
    }

    @DeleteMapping("/me/{categoryId}")
    public ResponseEntity<Object> delete(@PathVariable Long categoryId) throws AccessNotGrantedException {
        categoryService.deleteMe(categoryId);
        return ResponseEntity.ok(null);
    }
}
