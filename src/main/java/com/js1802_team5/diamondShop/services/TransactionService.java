package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.models.response_models.TransactionResponse;

public interface TransactionService {
        TransactionResponse updateOrderInfo(Integer orderId);
}
