package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;
import com.js1802_team5.diamondShop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Map<String, Object>> addToCart(@RequestParam("productID") int productID,
                                                         @RequestParam("productType") ProductType productType,
                                                         @RequestParam("customerID") int customerID) {
        String cartId = cartService.addToCart(productID, productType, customerID);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product added to cart successfully");
        response.put("cartId", cartId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-cart-by-customer-id/{customerID}")
    public CartResponse getCartByCustomerID(@PathVariable int customerID) {
        return cartService.getCartByCustomerID(customerID);
    }

    @PostMapping("/update-cart")
    public ResponseEntity<?> updateCart(
            @RequestParam("customerID") int customerID,
            @RequestParam("productType") ProductType productType,
            @RequestParam("productID") int productID,
            @RequestParam("quantity") int quantity) {
        try {
            CartResponse cartResponse = cartService.updateCart(customerID, productType, productID, quantity);
            return ResponseEntity.ok(cartResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/delete-cart-item")
    public ResponseEntity<?> deleteCartItem(
            @RequestParam("customerID") int customerID,
            @RequestParam("productID") int productID,
            @RequestParam("productType") ProductType productType) {
        try {
            cartService.deleteCart(customerID, productID, productType);
            CartResponse updatedCart = cartService.getCartByCustomerID(customerID);
            if(!updatedCart.getItems().isEmpty())
                return ResponseEntity.ok(updatedCart);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart is empty.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
