package com.js1802_team5.diamondShop.models.response_models;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String cartId;
    private Integer customerID;
    private List<CartItemResponse> items;
    private double totalPrice;

    public <E> CartResponse(String cartId, Integer customerID, List<CartItemResponse> items) {
        this.cartId = cartId;
        this.customerID = customerID;
        this.items = items;
        this.totalPrice = getTotalPrice();
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItemResponse::getAmount).sum();
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
        this.totalPrice = getTotalPrice(); // Update totalPrice when items change
    }
}
