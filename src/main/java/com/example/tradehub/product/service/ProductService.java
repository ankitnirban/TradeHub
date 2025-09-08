package com.example.tradehub.product.service;

import com.example.tradehub.exception.CategoryNotFoundException;
import com.example.tradehub.exception.ProductNotFoundException;
import com.example.tradehub.product.dto.request.ProductCreateRequestDto;
import com.example.tradehub.product.dto.request.ProductPatchRequestDto;
import com.example.tradehub.product.dto.request.ProductUpdateRequestDto;
import com.example.tradehub.product.dto.response.ProductResponseDto;
import com.example.tradehub.product.model.Category;
import com.example.tradehub.product.model.Product;
import com.example.tradehub.product.repository.CategoryRepository;
import com.example.tradehub.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::toProductResponseDto).toList();
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));
        return toProductResponseDto(product);
    }

    public ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Category category = categoryRepository.findById(productCreateRequestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productCreateRequestDto.getCategoryId() + " not found."));

        Product newProduct = toProductEntity(productCreateRequestDto, category);
        return toProductResponseDto(productRepository.save(newProduct));
    }

    public ProductResponseDto updateProduct(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        Category category = categoryRepository.findById(productUpdateRequestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productUpdateRequestDto.getCategoryId() + " not found."));

        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));
        
        existingProduct.setName(productUpdateRequestDto.getName());
        existingProduct.setDescription(productUpdateRequestDto.getDescription());
        existingProduct.setPrice(productUpdateRequestDto.getPrice());
        existingProduct.setQuantityInStock(productUpdateRequestDto.getQuantityInStock());
        existingProduct.setImageUrl(productUpdateRequestDto.getImageUrl());
        existingProduct.setCategory(category);
        
        return toProductResponseDto(productRepository.save(existingProduct));
    }

    public ProductResponseDto patchProduct(Long id, ProductPatchRequestDto productPatchRequestDto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));
        
        if (productPatchRequestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productPatchRequestDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productPatchRequestDto.getCategoryId() + " not found."));
            existingProduct.setCategory(category);
        }
        if (productPatchRequestDto.getName() != null) {
            existingProduct.setName(productPatchRequestDto.getName());
        }
        if (productPatchRequestDto.getDescription() != null) {
            existingProduct.setDescription(productPatchRequestDto.getDescription());
        }
        if (productPatchRequestDto.getPrice() != null) {
            existingProduct.setPrice(productPatchRequestDto.getPrice());
        }
        if (productPatchRequestDto.getQuantityInStock() != null) {
            existingProduct.setQuantityInStock(productPatchRequestDto.getQuantityInStock());
        }
        if (productPatchRequestDto.getImageUrl() != null) {
            existingProduct.setImageUrl(productPatchRequestDto.getImageUrl());
        }

        return toProductResponseDto(productRepository.save(existingProduct));
    }

    private Product toProductEntity(ProductCreateRequestDto productCreateRequestDto, Category category) {
        Product product = new Product(
            null,
            productCreateRequestDto.getName(),
            productCreateRequestDto.getDescription(),
            productCreateRequestDto.getPrice(),
            productCreateRequestDto.getQuantityInStock(),
            productCreateRequestDto.getImageUrl(), 
            category
        );
        return product;
    }

    private ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantityInStock(),
            product.getImageUrl(),
            product.getCategory().getName()
        );
    }
}
