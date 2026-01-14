package com.attemp3.sc.dtos.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadOneProductResponse {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;
    private double discount;
    private String brand;
    private String imageUrl;
    private boolean available;
    private LocalDateTime releaseDate;
}
