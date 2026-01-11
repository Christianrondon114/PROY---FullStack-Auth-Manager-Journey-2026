package com.attemp3.sc.dtos.product.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private double price;

    @Min(0)
    private int stock;

    @NotBlank
    private String category;

    @DecimalMin(value = "0.0", inclusive = true)
    private double discount;

    @NotBlank
    private String brand;

    @NotBlank
    private String imageUrl;

    private boolean available;
}
