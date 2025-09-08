package com.example.tradehub.product.service;

import com.example.tradehub.exception.CategoryNotFoundException;
import com.example.tradehub.exception.ProductNotFoundException;
import com.example.tradehub.product.dto.request.ProductCreateRequestDto;
import com.example.tradehub.product.dto.request.ProductPatchRequestDto;
import com.example.tradehub.product.dto.request.ProductUpdateRequestDto;
import com.example.tradehub.product.mapper.ProductMapper;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));
    }

    public Product createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Product newProduct = ProductMapper.toEntity(productCreateRequestDto);
        Category category = categoryRepository.findById(productCreateRequestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productCreateRequestDto.getCategoryId() + " not found."));
        newProduct.setCategory(category);
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        Product existingProduct = findProductById(id);
        Category category = categoryRepository.findById(productUpdateRequestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productUpdateRequestDto.getCategoryId() + " not found."));
        ProductMapper.apply(productUpdateRequestDto, existingProduct);
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);
    }

    public Product patchProduct(Long id, ProductPatchRequestDto productPatchRequestDto) {
        Product existingProduct = findProductById(id);
        ProductMapper.applyPatch(productPatchRequestDto, existingProduct);
        if (productPatchRequestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productPatchRequestDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category with id " + productPatchRequestDto.getCategoryId() + " not found."));
            existingProduct.setCategory(category);
        }
        return productRepository.save(existingProduct);
    }
}
