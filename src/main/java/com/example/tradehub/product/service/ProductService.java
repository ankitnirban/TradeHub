package com.example.tradehub.product.service;

import com.example.tradehub.exception.CategoryNotFoundException;
import com.example.tradehub.exception.ProductNotFoundException;
import com.example.tradehub.product.model.Category;
import com.example.tradehub.product.model.Product;
import com.example.tradehub.product.model.ProductCreateRequestDto;
import com.example.tradehub.product.model.ProductPatchRequestDto;
import com.example.tradehub.product.model.ProductUpdateRequestDto;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));
    }

    public Product createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Product newProduct = new Product();

        Category category = categoryRepository.findById(productCreateRequestDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category with id " + productCreateRequestDto.getCategoryId() + " not found."));

        newProduct.setName(productCreateRequestDto.getName());
        newProduct.setDescription(productCreateRequestDto.getDescription());
        newProduct.setPrice(productCreateRequestDto.getPrice());
        newProduct.setQuantityInStock(productCreateRequestDto.getQuantityInStock());
        newProduct.setImageUrl(productCreateRequestDto.getImageUrl());
        newProduct.setCategory(category);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        Product existingProduct = findProductById(id);

        Category category = categoryRepository.findById(productUpdateRequestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productUpdateRequestDto.getCategoryId() + " not found."));

        existingProduct.setName(productUpdateRequestDto.getName());
        existingProduct.setDescription(productUpdateRequestDto.getDescription());
        existingProduct.setPrice(productUpdateRequestDto.getPrice());
        existingProduct.setQuantityInStock(productUpdateRequestDto.getQuantityInStock());
        existingProduct.setImageUrl(productUpdateRequestDto.getImageUrl());
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    public Product patchProduct(Long id, ProductPatchRequestDto productPatchRequestDto) {
        Product existingProduct = findProductById(id);

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
        if (productPatchRequestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productPatchRequestDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productPatchRequestDto.getCategoryId() + " not found."));
            existingProduct.setCategory(category);
        }

        return productRepository.save(existingProduct);
    }
}
