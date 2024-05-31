package com.js1802_team5.diamondShop.models.response_models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String cartId;
    private Integer customerID;
    private List<CartItemResponse> items;
}
