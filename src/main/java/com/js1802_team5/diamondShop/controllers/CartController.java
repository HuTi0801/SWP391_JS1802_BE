package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.CartRequest;
import com.js1802_team5.diamondShop.models.response_models.Cart;
import com.js1802_team5.diamondShop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    public final CartService cartService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody CartRequest cartRequest) {
        String cartId = cartService.addToCart(cartRequest.getProductID(), cartRequest.getProductType(), cartRequest.getCustomerID());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product added to cart successfully");
        response.put("cartId", cartId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/view-cart")
    public Cart viewCart(@RequestBody CartRequest cartRequest) {
        return cartService.getCart(cartRequest.getCartID(), cartRequest.getCustomerID());
    }
}
