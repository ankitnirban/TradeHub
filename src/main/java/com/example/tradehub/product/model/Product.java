package com.example.tradehub.product.model;

import com.example.tradehub.product.repository.CategoryRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantityInStock;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public static Product toProductEntity(ProductCreateRequest productCreateRequest, Category category) {
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
