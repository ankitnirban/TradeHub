package com.example.tradehub.product.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
