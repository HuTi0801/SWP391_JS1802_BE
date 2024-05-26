package com.js1802_team5.diamondShop.models.request_models;

import com.js1802_team5.diamondShop.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {
    private int productID;
    private ProductType productType;
    private int customerID;
    private String CartID;
}
