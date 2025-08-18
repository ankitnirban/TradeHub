package com.example.tradehub.product.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPatchRequest {

    private String name;

    private String description;

    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantityInStock;

    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;

    private Long categoryId;
}