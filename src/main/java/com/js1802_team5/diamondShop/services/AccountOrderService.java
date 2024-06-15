package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.Response;

public interface AccountOrderService {
    Response assignStaffToOrder(Integer accountId, Integer orderId);
}
