package com.js1802_team5.diamondShop.models.request_models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondRequest {
    private Integer id;
    private String origin;
    private String clarity;
    private float caratWeight;
    private double price;
    private String color;
    private String cut;
    private String certificateNumber;
    private int quantity;
    private String imageDiamond;
    private double min_price;
    private double max_price;
}
