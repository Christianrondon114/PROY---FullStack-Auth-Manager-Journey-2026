package com.attemp3.sc.repository;

import com.attemp3.sc.entities.CartItem;
import com.attemp3.sc.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByShoppingCart(ShoppingCart cart);
}
