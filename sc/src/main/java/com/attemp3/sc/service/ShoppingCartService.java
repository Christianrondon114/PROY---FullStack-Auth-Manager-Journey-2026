package com.attemp3.sc.service;

import com.attemp3.sc.dtos.shoppingcart.response.AddToCartRequest;
import com.attemp3.sc.dtos.shoppingcart.response.AddToCartResponse;
import com.attemp3.sc.dtos.shoppingcart.response.ShoppingCartResponse;
import com.attemp3.sc.entities.CartItem;
import com.attemp3.sc.entities.Product;
import com.attemp3.sc.entities.ShoppingCart;
import com.attemp3.sc.entities.User;
import com.attemp3.sc.mapper.ShoppingCartMapper;
import com.attemp3.sc.repository.CartItemRepository;
import com.attemp3.sc.repository.ProductRepository;
import com.attemp3.sc.repository.ShoppingCartRepository;
import com.attemp3.sc.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;


    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               UserRepository userRepository,
                               ProductRepository productRepository,
                               CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public AddToCartResponse addToCart(AddToCartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User with" + request.getUserId() + "doesn't exists"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product with" + request.getProductId() + "doesn't exists"));

        ShoppingCart sc = shoppingCartRepository.findByUser_Id(request.getUserId()).
                orElseGet(() -> createShoppingCart(user));

        CartItem item = CartItem.builder()
                .shoppingCart(sc)
                .product(product)
                .quantity(request.getQuantity())
                .subtotal(request.getQuantity() * product.getPrice())
                .build();

        CartItem savedItem = cartItemRepository.save(item);
        updateTotalPrice(sc);
        shoppingCartRepository.save(sc);

        return ShoppingCartMapper.toResponse(savedItem);
    }

    private void clearAllCart(Long id) {
        ShoppingCart cart = shoppingCartRepository.findByUser_Id(id)
                .orElseThrow(() -> new RuntimeException("Shopping Cart not found"));
        cart.getListItems().clear();
        updateTotalPrice(cart);
        shoppingCartRepository.save(cart);

    }

    private void clearItemCart(Long userId, Long cartItemId) {
        ShoppingCart cart = shoppingCartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        cart.getListItems().
                removeIf(item -> item.getId().equals(cartItemId));
        updateTotalPrice(cart);
        shoppingCartRepository.save(cart);
    }

    private ShoppingCart createShoppingCart(User user) {
        ShoppingCart newCart = new ShoppingCart();
        newCart.setUser(user);
        newCart.setListItems(new ArrayList<>());
        return shoppingCartRepository.save(newCart);
    }

    public void updateTotalPrice(ShoppingCart cart) {
        double total = cart.getListItems()
                .stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        cart.setTotalPrice(total);
    }

}
