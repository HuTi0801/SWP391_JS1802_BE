package com.js1802_team5.diamondShop.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondResponse {
    private Integer id;
    private String name;
    private String origin;
    private String clarity;
    private float caratWeight;
    private double price;
    private String color;
    private String cut;
    private String certificateNumber;
    private int quantity;
    private String imageDiamond;
    private boolean statusDiamond;
    private Integer accountId; // Thêm trường này
}
