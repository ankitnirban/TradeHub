package com.example.tradehub.product.service;

import com.example.tradehub.exception.CategoryNotFoundException;
import com.example.tradehub.product.dto.request.CategoryCreateRequestDto;
import com.example.tradehub.product.dto.request.CategoryUpdateRequestDto;
import com.example.tradehub.product.mapper.CategoryMapper;
import com.example.tradehub.product.model.Category;
import com.example.tradehub.product.repository.CategoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(
                        String.format("Category with id %d not found", id)));
    }

    public Category createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
        Category category = CategoryMapper.toEntity(categoryCreateRequestDto);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category existingCategory = getCategoryById(id);
        CategoryMapper.apply(categoryUpdateRequestDto, existingCategory);
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        Category existingCategory = getCategoryById(id);
        categoryRepository.delete(existingCategory);
    }

    // Mapping moved to CategoryMapper
}