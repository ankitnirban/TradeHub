package com.example.tradehub.product.controller;

import com.example.tradehub.product.dto.request.CategoryCreateRequestDto;
import com.example.tradehub.product.dto.request.CategoryUpdateRequestDto;
import com.example.tradehub.product.dto.response.CategoryResponseDto;
import com.example.tradehub.product.mapper.CategoryMapper;
import com.example.tradehub.product.model.Category;
import com.example.tradehub.product.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories.stream().map(CategoryMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryMapper.toResponse(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryCreateRequestDto categoryCreateRequestDto) {
        Category createdCategory = categoryService.createCategory(categoryCreateRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();
        return ResponseEntity.created(location).body(CategoryMapper.toResponse(createdCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category updatedCategory = categoryService.updateCategory(id, categoryUpdateRequestDto);
        return ResponseEntity.ok(CategoryMapper.toResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
