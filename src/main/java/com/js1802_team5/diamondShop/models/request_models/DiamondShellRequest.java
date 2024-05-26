package com.js1802_team5.diamondShop.models.request_models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondShellRequest {
    private Integer id;
    private int quantity;
    private String secondaryStoneType;
    private String material;
    private String gender;
    private double price;
    private String imageDiamondShell;
    private double min_price;
    private double max_price;
}
