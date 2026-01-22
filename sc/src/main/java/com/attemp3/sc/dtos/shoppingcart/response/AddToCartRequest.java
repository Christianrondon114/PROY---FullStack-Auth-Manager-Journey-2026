package com.attemp3.sc.dtos.shoppingcart.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddToCartRequest {
    private Long productId;
    private int quantity;
}
