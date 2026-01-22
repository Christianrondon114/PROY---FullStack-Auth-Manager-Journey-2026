package com.attemp3.sc.mapper;

import com.attemp3.sc.dtos.shoppingcart.response.AddToCartResponse;
import com.attemp3.sc.entities.CartItem;

public class ShoppingCartMapper {

    public static AddToCartResponse toResponse(CartItem savedItem) {
        if (savedItem == null) return null;

        AddToCartResponse response = new AddToCartResponse();

        response.setItemId(savedItem.getId());
        response.setProductName(savedItem.getProduct().getName());
        response.setImageUrl(savedItem.getProduct().getImageUrl());
        response.setQuantity(savedItem.getQuantity());
        response.setProductPrice(savedItem.getProduct().getPrice());
        response.setSubtotal(savedItem.getSubtotal());

        return response;
    }
}
