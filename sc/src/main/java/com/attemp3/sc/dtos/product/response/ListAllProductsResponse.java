package com.attemp3.sc.dtos.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListAllProductsResponse {
    private Long productId;
    private String name;
    private double price;
    private int stock;
    private boolean available;
}
