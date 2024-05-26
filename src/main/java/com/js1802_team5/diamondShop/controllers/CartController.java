package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.CartRequest;
import com.js1802_team5.diamondShop.models.response_models.Cart;
import com.js1802_team5.diamondShop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
        cartService.addToCart(cartRequest.getProductID(), cartRequest.getProductType(), cartRequest.getCustomerID());
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @PostMapping("/view-cart")
    public Cart viewCart(@RequestBody CartRequest cartRequest) {
        return cartService.getCart(cartRequest.getCartID(), cartRequest.getCustomerID());
    }
}
