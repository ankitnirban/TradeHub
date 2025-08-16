package com.example.tradehub.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Product(
        @Id
        Long id,
        String name,
        String description
) {}
