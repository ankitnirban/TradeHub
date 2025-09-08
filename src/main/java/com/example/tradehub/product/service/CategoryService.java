package com.example.tradehub.product.service;

import com.example.tradehub.exception.CategoryNotFoundException;
import com.example.tradehub.product.dto.request.CategoryCreateRequestDto;
import com.example.tradehub.product.dto.request.CategoryUpdateRequestDto;
import com.example.tradehub.product.dto.response.CategoryResponseDto;
import com.example.tradehub.product.model.Category;
import com.example.tradehub.product.repository.CategoryRepository;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toCategoryResponseDto).toList();
    }

    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new CategoryNotFoundException(String.format("Category with id %d not found", id));
        });
        return toCategoryResponseDto(category);
    }

    public CategoryResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
        Category createdCategory = categoryRepository.save(toCategoryEntity(categoryCreateRequestDto));
        return toCategoryResponseDto(createdCategory);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> {
            throw new CategoryNotFoundException(String.format("Category with id %d not found", id));
        });
        existingCategory.setName(categoryUpdateRequestDto.getName());
        existingCategory.setDescription(categoryUpdateRequestDto.getDescription());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return toCategoryResponseDto(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> {
            throw new CategoryNotFoundException(String.format("Category with id %d not found", id));
        });
        categoryRepository.delete(existingCategory);
    }

    private CategoryResponseDto toCategoryResponseDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
        return categoryResponseDto;
    }

    private Category toCategoryEntity(CategoryCreateRequestDto categoryCreateRequestDto) {
        Category category = new Category(
            null,
            categoryCreateRequestDto.getName(),
            categoryCreateRequestDto.getDescription(),
            Collections.emptyList()
        );
        return category;
    }
}