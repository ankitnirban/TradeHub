package com.example.tradehub.product.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
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
}
