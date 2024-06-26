package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.CategoryDto;
import com.lag.todoapp.rest.todoapprest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryDto>> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByUserId(userId));
    }
}
