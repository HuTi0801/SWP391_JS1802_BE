package com.js1802_team5.diamondShop.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
    private Integer productId;
    private int quantity;
    private double price;
    private int size;
    private String diamondId;
    private String diamondShellId;
}
