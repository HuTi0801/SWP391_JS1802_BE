package com.js1802_team5.diamondShop.models.response_models;
import com.js1802_team5.diamondShop.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private int productId;
    private ProductType productType;
    private int quantity;
    private double unitPrice;
    private double amount;
}
