package com.js1802_team5.diamondShop.models.request_models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondShellSearchRequest {
    private String secondaryStoneType;
    private String material;
    private String gender;
    private double min_price;
    private double max_price;
}
