package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.TransactionResponse;
import com.js1802_team5.diamondShop.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/update-order-info/{orderId}")
    @PreAuthorize("hasAuthority('manager:update')")
    public TransactionResponse updateOrderInfo(@PathVariable Integer orderId){
        return transactionService.updateOrderInfo(orderId);
    }
}
