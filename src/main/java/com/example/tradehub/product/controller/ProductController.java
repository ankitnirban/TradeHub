package com.example.tradehub.product.controller;

import com.example.tradehub.product.model.Product;
import com.example.tradehub.product.model.ProductCreateRequest;
import com.example.tradehub.product.model.ProductPatchRequest;
import com.example.tradehub.product.model.ProductResponse;
import com.example.tradehub.product.model.ProductUpdateRequest;
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
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        Product createdProduct = productService.createProduct(productCreateRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(ProductResponse.fromEntity(createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest updateRequest) {
        Product updatedProduct = productService.updateProduct(id, updateRequest);
        return ResponseEntity.ok(ProductResponse.fromEntity(updatedProduct));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> patchProduct(@PathVariable Long id, @Valid @RequestBody ProductPatchRequest patchRequest) {
        Product patchedProduct = productService.patchProduct(id, patchRequest);
        return ResponseEntity.ok(ProductResponse.fromEntity(patchedProduct));
    }
}
