package com.attemp3.sc.dtos.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardProductResponse {
    private String name;
    private String description;
    private double price;
    private double discount;
    private String imageUrl;
}
