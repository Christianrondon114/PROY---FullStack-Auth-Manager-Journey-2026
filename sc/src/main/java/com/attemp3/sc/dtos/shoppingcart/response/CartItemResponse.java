package com.attemp3.sc.dtos.shoppingcart.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private double productPrice;
    private String imageUrl;
    private int quantity;
    private double subtotal;
}
