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
import org.springframework.security.core.userdetails.UserDetails;
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

    public ShoppingCartResponse showAllItems(UserDetails userDetails){
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return shoppingCartRepository.findByUser_Id(user.getId())
                .map(ShoppingCartMapper::toShoppingCartResponse)
                .orElseGet(() -> {
                    ShoppingCartResponse empty = new ShoppingCartResponse();
                    empty.setListItems(new ArrayList<>());
                    empty.setTotalPrice(0.0);
                    return empty;
                });
    }

    public AddToCartResponse addToCart(UserDetails userDetails, AddToCartRequest request) {
        // DEBUG:
        System.out.println("DEBUG - ID Producto recibido: " + request.getProductId());
        System.out.println("DEBUG - Username recibido: " + userDetails.getUsername());

        // Si este ID es null, aquí es donde explota findById
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product with" + request.getProductId() + "doesn't exists"));

        ShoppingCart sc = shoppingCartRepository.findByUser_Id(user.getId()).
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

    public void clearAllCart(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart cart = shoppingCartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Shopping Cart not found"));
        cart.getListItems().clear();
        updateTotalPrice(cart);
        shoppingCartRepository.save(cart);
    }

    public void clearItemCart(UserDetails userDetails, Long cartItemId) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart cart = shoppingCartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Shopping Cart not founded"));

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

    private void updateTotalPrice(ShoppingCart cart) {
        double total = cart.getListItems()
                .stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        cart.setTotalPrice(total);
    }

}
