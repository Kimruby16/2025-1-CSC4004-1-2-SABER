package com.oss.saber.controller;

import com.oss.saber.domain.Category;
import com.oss.saber.dto.CategoryResponse;
import com.oss.saber.dto.DefaultVerificationResponse;
import com.oss.saber.dto.mapper.CategoryMapper;
import com.oss.saber.dto.mapper.DefaultVerificationMapper;
import com.oss.saber.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> responses = categories.stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<DefaultVerificationResponse>> getDefaultVerifications(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        List<DefaultVerificationResponse> responses = category.getDefaultVerifications().stream()
                .map(DefaultVerificationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}