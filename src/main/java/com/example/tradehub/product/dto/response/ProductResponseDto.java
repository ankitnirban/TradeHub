package com.example.tradehub.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.example.tradehub.product.model.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;
    private String imageUrl;
    private String categoryName;

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}