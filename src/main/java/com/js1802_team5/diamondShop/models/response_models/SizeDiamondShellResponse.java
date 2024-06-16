package com.js1802_team5.diamondShop.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeDiamondShellResponse {
    private Integer id;
    private Integer size;
}
