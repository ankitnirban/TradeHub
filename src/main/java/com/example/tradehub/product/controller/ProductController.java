package com.example.tradehub.product.controller;

import com.example.tradehub.product.dto.request.ProductCreateRequestDto;
import com.example.tradehub.product.dto.request.ProductPatchRequestDto;
import com.example.tradehub.product.dto.request.ProductUpdateRequestDto;
import com.example.tradehub.product.dto.response.ProductResponseDto;
import com.example.tradehub.product.mapper.ProductMapper;
import com.example.tradehub.product.model.Product;
import com.example.tradehub.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts().stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable Long id) {
        Product product = productService.findProductById(id);
        ProductResponseDto productResponseDto = ProductMapper.toResponse(product);
        return ResponseEntity.ok(productResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        Product createdProduct = productService.createProduct(productCreateRequestDto);
        ProductResponseDto createdProductResponseDto = ProductMapper.toResponse(createdProduct);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProductResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        Product updatedProduct = productService.updateProduct(id, productUpdateRequestDto);
        ProductResponseDto updatedProductResponseDto = ProductMapper.toResponse(updatedProduct);
        return ResponseEntity.ok(updatedProductResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> patchProduct(@PathVariable Long id, @Valid @RequestBody ProductPatchRequestDto productPatchRequestDto) {
        Product patchedProduct = productService.patchProduct(id, productPatchRequestDto);
        ProductResponseDto patchedProductResponseDto = ProductMapper.toResponse(patchedProduct);
        return ResponseEntity.ok(patchedProductResponseDto);
    }
}
