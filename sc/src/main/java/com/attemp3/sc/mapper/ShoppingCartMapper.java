package com.attemp3.sc.mapper;

import com.attemp3.sc.dtos.shoppingcart.response.CartItemResponse;
import com.attemp3.sc.dtos.shoppingcart.response.ShoppingCartResponse;
import com.attemp3.sc.entities.CartItem;
import com.attemp3.sc.entities.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartMapper {

    public static CartItemResponse toResponse(CartItem savedItem) {
        if (savedItem == null) return null;

        CartItemResponse response = new CartItemResponse();

        response.setCartItemId(savedItem.getId());
        response.setProductName(savedItem.getProduct().getName());
        response.setImageUrl(savedItem.getProduct().getImageUrl());
        response.setQuantity(savedItem.getQuantity());
        response.setProductPrice(savedItem.getProduct().getPrice());
        response.setSubtotal(savedItem.getSubtotal());

        return response;
    }

    public static ShoppingCartResponse toShoppingCartResponse(ShoppingCart cart) {
        if (cart == null) return null;

        ShoppingCartResponse response = new ShoppingCartResponse();

        response.setTotalPrice(cart.getTotalPrice());

        List<CartItemResponse> items = cart.getListItems()
                .stream()
                .map(ShoppingCartMapper::toResponse)
                .collect(Collectors.toList());

        response.setListItems(items);

        return response;
    }
}
