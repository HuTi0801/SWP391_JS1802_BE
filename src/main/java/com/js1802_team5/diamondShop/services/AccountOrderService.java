package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.AccountResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;

import java.util.List;

public interface AccountOrderService {
    Response assignStaffToOrder(Integer accountId, Integer orderId);

    List<AccountResponse> getAccountsByOrderId(Integer orderId);
}
