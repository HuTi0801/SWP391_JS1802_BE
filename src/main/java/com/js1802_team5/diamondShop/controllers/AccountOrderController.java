package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.AccountOrderService;
import com.js1802_team5.diamondShop.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/account-order")
@RequiredArgsConstructor
public class AccountOrderController {
    private final AccountOrderService accountOrderService;
    @PostMapping("/assign-staff-to-order")
    public Response assignStaffToOrder(@RequestParam Integer accountId, @RequestParam Integer orderId) {
        try {
            return accountOrderService.assignStaffToOrder(accountId, orderId);
        } catch (Exception e) {
            return new Response(null, false, e.getMessage(), 500);
        }
    }
}
