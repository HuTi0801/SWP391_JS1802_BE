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
public class DiamondShellResponse {
    private Integer id;
    private String name;
    private int quantity;
    private String secondaryStoneType;
    private String material;
    private String gender;
    private double price;
    private String imageDiamondShell;
    private List<SizeDiamondShellResponse> size;
    private Integer accountId;
}
