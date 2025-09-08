package com.example.tradehub.product.controller;

import com.example.tradehub.product.model.Product;
import com.example.tradehub.product.model.ProductCreateRequestDto;
import com.example.tradehub.product.model.ProductPatchRequestDto;
import com.example.tradehub.product.model.ProductResponse;
import com.example.tradehub.product.model.ProductUpdateRequestDto;
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
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts().stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(ProductResponse.fromEntity(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        Product createdProduct = productService.createProduct(productCreateRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(ProductResponse.fromEntity(createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        Product updatedProduct = productService.updateProduct(id, productUpdateRequestDto);
        return ResponseEntity.ok(ProductResponse.fromEntity(updatedProduct));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> patchProduct(@PathVariable Long id, @Valid @RequestBody ProductPatchRequestDto productPatchRequestDto) {
        Product patchedProduct = productService.patchProduct(id, productPatchRequestDto);
        return ResponseEntity.ok(ProductResponse.fromEntity(patchedProduct));
    }
}
