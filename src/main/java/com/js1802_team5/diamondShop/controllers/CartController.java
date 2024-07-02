package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/cart")
@RequiredArgsConstructor
public class CartController {

    public final CartService cartService;

    @PostMapping("/add-to-cart")
//    @PreAuthorize("hasAuthority('customer:create')")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestParam("productID") int productID,
                                                         @RequestParam("productType") ProductType productType,
                                                         @RequestParam("customerID") int customerID,
                                                         @RequestParam(value = "size", required = false) Integer size) {
        Response serviceResponse = cartService.addToCart(productID, productType, customerID, size);
        Map<String, Object> response = new HashMap<>();

        if (serviceResponse.isSuccess()) {
            response.put("message", serviceResponse.getMessage());
            response.put("cartId", ((CartResponse) serviceResponse.getResult()).getCartId());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", serviceResponse.getMessage());
            return ResponseEntity.status(serviceResponse.getStatusCode()).body(response);
        }
    }

    @PostMapping("/get-cart-by-customer-id/{customerID}")
    public ResponseEntity<?> getCartByCustomerID(@PathVariable int customerID) {
        if (cartService.getCartByCustomerID(customerID) != null)
            return ResponseEntity.ok(cartService.getCartByCustomerID(customerID));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart does not exist.");
    }

    @PostMapping("/update-cart")
    public ResponseEntity<?> updateCart(
            @RequestParam("customerID") int customerID,
            @RequestParam("productType") ProductType productType,
            @RequestParam("productID") int productID,
            @RequestParam("quantity") int quantity) {
        Response serviceResponse = cartService.updateCart(customerID, productType, productID, quantity);
        if (serviceResponse.isSuccess()) {
            return ResponseEntity.ok(serviceResponse.getResult());
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", serviceResponse.getMessage());
            return ResponseEntity.status(serviceResponse.getStatusCode()).body(response);
        }
    }

    @PostMapping("/delete-cart-item")
    public ResponseEntity<?> deleteCartItem(
            @RequestParam("customerID") int customerID,
            @RequestParam("productID") int productID,
            @RequestParam("productType") ProductType productType) {
        Response serviceResponse = cartService.deleteCart(customerID, productID, productType);
        if (serviceResponse.isSuccess()) {
            CartResponse updatedCart = (CartResponse) serviceResponse.getResult();
            if (updatedCart != null && !updatedCart.getItems().isEmpty()) {
                return ResponseEntity.ok(updatedCart);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Cart is empty.");
                return ResponseEntity.ok(response);
            }
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", serviceResponse.getMessage());
            return ResponseEntity.status(serviceResponse.getStatusCode()).body(response);
        }
    }

    @PostMapping("/apply-promotion")
    public ResponseEntity<Response> applyPromotion(@RequestParam String cartId, @RequestParam String promotionCode, @RequestParam Integer customerID) {
        Response response = cartService.applyPromotion(cartId, promotionCode, customerID);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/remove-applying-promotion-code")
    public Response removePromotion(@RequestParam String cartId, @RequestParam Integer customerId) {
        return cartService.removePromotion(cartId, customerId);
    }

    // Endpoint đồng bộ hóa giỏ hàng trước khi thanh toán
    @PostMapping("/refresh/{customerID}")
    public Response refreshCart(@PathVariable int customerID) {
        return cartService.refreshCart(customerID);
    }
}
