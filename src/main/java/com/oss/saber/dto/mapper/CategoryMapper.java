package com.oss.saber.dto.mapper;

import com.oss.saber.domain.Category;
import com.oss.saber.dto.CategoryResponse;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
