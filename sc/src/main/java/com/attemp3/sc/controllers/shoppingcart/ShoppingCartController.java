package com.attemp3.sc.controllers.shoppingcart;

import com.attemp3.sc.dtos.shoppingcart.response.AddToCartRequest;
import com.attemp3.sc.dtos.shoppingcart.response.AddToCartResponse;
import com.attemp3.sc.dtos.shoppingcart.response.ShoppingCartResponse;
import com.attemp3.sc.entities.User;
import com.attemp3.sc.repository.UserRepository;
import com.attemp3.sc.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;


    public ShoppingCartController(ShoppingCartService shoppingCartService
    ) {
        this.shoppingCartService = shoppingCartService;

    }

    @GetMapping(path = "/my-cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingCartResponse> showAllItems(@AuthenticationPrincipal UserDetails userDetails) {
        ShoppingCartResponse response = shoppingCartService.showAllItems(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddToCartResponse> addToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddToCartRequest request) {
        AddToCartResponse response = shoppingCartService.addToCart(userDetails, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/clear-item")
    public ResponseEntity<Void> clearItemCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long cartItemId) {
        shoppingCartService.clearItemCart(userDetails, cartItemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(path = "/clear-all")
    public ResponseEntity<Void> clearAllCart(@AuthenticationPrincipal UserDetails userDetails) {
        shoppingCartService.clearAllCart(userDetails);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
