package com.js1802_team5.diamondShop.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String vnp_OrderInfo;
    public boolean isSuccess;
    public String message;
    public int statusCode;
}
