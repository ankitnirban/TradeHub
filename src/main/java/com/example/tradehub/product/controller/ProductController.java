package com.example.tradehub.product.controller;

import com.example.tradehub.product.dto.request.ProductCreateRequestDto;
import com.example.tradehub.product.dto.request.ProductPatchRequestDto;
import com.example.tradehub.product.dto.request.ProductUpdateRequestDto;
import com.example.tradehub.product.dto.response.ProductResponseDto;
import com.example.tradehub.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        ProductResponseDto createdProductResponseDto = productService.createProduct(productCreateRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProductResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProductResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productUpdateRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> patchProduct(@PathVariable Long id, @Valid @RequestBody ProductPatchRequestDto productPatchRequestDto) {
        return ResponseEntity.ok(productService.patchProduct(id, productPatchRequestDto));
    }
}
