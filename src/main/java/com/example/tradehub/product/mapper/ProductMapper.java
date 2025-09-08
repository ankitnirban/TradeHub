package com.example.tradehub.product.mapper;

import com.example.tradehub.product.dto.request.ProductCreateRequestDto;
import com.example.tradehub.product.dto.request.ProductPatchRequestDto;
import com.example.tradehub.product.dto.request.ProductUpdateRequestDto;
import com.example.tradehub.product.model.Product;
import com.example.tradehub.product.dto.response.ProductResponseDto;

public final class ProductMapper {

    private ProductMapper() {}

    public static Product toEntity(ProductCreateRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantityInStock(dto.getQuantityInStock());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    public static void apply(ProductUpdateRequestDto dto, Product target) {
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setPrice(dto.getPrice());
        target.setQuantityInStock(dto.getQuantityInStock());
        target.setImageUrl(dto.getImageUrl());
    }

    public static void applyPatch(ProductPatchRequestDto dto, Product target) {
        if (dto.getName() != null) target.setName(dto.getName());
        if (dto.getDescription() != null) target.setDescription(dto.getDescription());
        if (dto.getPrice() != null) target.setPrice(dto.getPrice());
        if (dto.getQuantityInStock() != null) target.setQuantityInStock(dto.getQuantityInStock());
        if (dto.getImageUrl() != null) target.setImageUrl(dto.getImageUrl());
    }

    public static ProductResponseDto toResponse(Product entity) {
        return ProductResponseDto.fromEntity(entity);
    }
}


