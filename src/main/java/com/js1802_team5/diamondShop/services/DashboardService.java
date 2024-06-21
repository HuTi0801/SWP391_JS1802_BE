package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.RevenueResponse;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    List<RevenueResponse> getRevenueByYear(int year);
    Map<String, Object> getPerformance(int year);
}
