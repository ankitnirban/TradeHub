package com.example.tradehub.product.dto.response;

import com.example.tradehub.product.model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;

    public static CategoryResponseDto fromEntity(Category category) {
        return new CategoryResponseDto(category.getId(), category.getName(), category.getDescription());
    }
}