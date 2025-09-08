package com.example.tradehub.product.mapper;

import com.example.tradehub.product.dto.request.CategoryCreateRequestDto;
import com.example.tradehub.product.dto.request.CategoryUpdateRequestDto;
import com.example.tradehub.product.dto.response.CategoryResponseDto;
import com.example.tradehub.product.model.Category;

import java.util.Collections;

public final class CategoryMapper {

    private CategoryMapper() {}

    public static Category toEntity(CategoryCreateRequestDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setProducts(Collections.emptyList());
        return category;
    }

    public static void apply(CategoryUpdateRequestDto dto, Category target) {
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
    }

    public static CategoryResponseDto toResponse(Category entity) {
        return new CategoryResponseDto(entity.getId(), entity.getName(), entity.getDescription());
    }
}


