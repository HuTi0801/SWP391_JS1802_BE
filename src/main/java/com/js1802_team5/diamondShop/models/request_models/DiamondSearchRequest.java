package com.js1802_team5.diamondShop.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondSearchRequest {
    private String origin;
    private String clarity;
    private float caratWeight;
    private String color;
    private String cut;
    private float min_price;
    private float max_price;
}
