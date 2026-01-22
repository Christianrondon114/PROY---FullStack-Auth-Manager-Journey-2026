package com.attemp3.sc.dtos.shoppingcart.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingCartResponse {
    private List<AddToCartResponse> listItems;
    private double totalPrice;
}
