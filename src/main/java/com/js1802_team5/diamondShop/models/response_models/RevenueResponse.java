package com.js1802_team5.diamondShop.models.response_models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueResponse {
    private int month;
    private String statusName;
    private List<OrderRevenueResponse> orderRevenues;
    private double totalRevenue;
}
