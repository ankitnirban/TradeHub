package com.example.tradehub.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProductCreateRequestDto {

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Quantity cannot be null")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantityInStock;

    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
}