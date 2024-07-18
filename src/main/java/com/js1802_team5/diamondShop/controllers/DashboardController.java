package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.RevenueResponse;
import com.js1802_team5.diamondShop.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("/view-revenue")
    @PreAuthorize("hasAuthority('manager:read')")
    public ResponseEntity<List<RevenueResponse>> getRevenue(@RequestParam int year) {
        List<RevenueResponse> revenueResponses = dashboardService.getRevenueByYear(year);
        return new ResponseEntity<>(revenueResponses, HttpStatus.OK);
    }

    @GetMapping("/view-staff-performance")
//    @PreAuthorize("hasAuthority('manager:read')")
    public Map<String, Object> getPerformance(@RequestParam int year) {
        return dashboardService.getPerformance(year);
    }
}
