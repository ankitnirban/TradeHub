package com.example.tradehub.product.controller;

import com.example.tradehub.product.dto.request.CategoryCreateRequestDto;
import com.example.tradehub.product.dto.request.CategoryUpdateRequestDto;
import com.example.tradehub.product.dto.response.CategoryResponseDto;
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
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryCreateRequestDto categoryCreateRequestDto) {
        CategoryResponseDto createdCategoryResponseDto = categoryService.createCategory(categoryCreateRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategoryResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdCategoryResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryUpdateRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
