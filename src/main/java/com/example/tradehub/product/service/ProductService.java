package com.example.tradehub.product.service;

import com.example.tradehub.exception.CategoryNotFoundException;
import com.example.tradehub.exception.ProductNotFoundException;
import com.example.tradehub.product.model.Category;
import com.example.tradehub.product.model.Product;
import com.example.tradehub.product.model.ProductCreateRequest;
import com.example.tradehub.product.model.ProductPatchRequest;
import com.example.tradehub.product.model.ProductUpdateRequest;
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

    public Product createProduct(ProductCreateRequest productCreateRequest) {
        Product newProduct = toProductEntity(productCreateRequest);
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductUpdateRequest updateRequest) {
        Product existingProduct = findProductById(id);

        Category category = categoryRepository.findById(updateRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + updateRequest.getCategoryId() + " not found."));

        existingProduct.setName(updateRequest.getName());
        existingProduct.setDescription(updateRequest.getDescription());
        existingProduct.setPrice(updateRequest.getPrice());
        existingProduct.setQuantityInStock(updateRequest.getQuantityInStock());
        existingProduct.setImageUrl(updateRequest.getImageUrl());
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    public Product patchProduct(Long id, ProductPatchRequest patchRequest) {
        Product existingProduct = findProductById(id);

        if (patchRequest.getName() != null) {
            existingProduct.setName(patchRequest.getName());
        }
        if (patchRequest.getDescription() != null) {
            existingProduct.setDescription(patchRequest.getDescription());
        }
        if (patchRequest.getPrice() != null) {
            existingProduct.setPrice(patchRequest.getPrice());
        }
        if (patchRequest.getQuantityInStock() != null) {
            existingProduct.setQuantityInStock(patchRequest.getQuantityInStock());
        }
        if (patchRequest.getImageUrl() != null) {
            existingProduct.setImageUrl(patchRequest.getImageUrl());
        }
        if (patchRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(patchRequest.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category with id " + patchRequest.getCategoryId() + " not found."));
            existingProduct.setCategory(category);
        }

        return productRepository.save(existingProduct);
    }

    private Product toProductEntity(ProductCreateRequest productCreateRequest) {
        Category category = categoryRepository.findById(productCreateRequest.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category with id " + productCreateRequest.getCategoryId() + " not found."));
        return new Product(
                null,
                productCreateRequest.getName(),
                productCreateRequest.getDescription(),
                productCreateRequest.getPrice(),
                productCreateRequest.getQuantityInStock(),
                productCreateRequest.getImageUrl(),
                category
        );
    }
}
