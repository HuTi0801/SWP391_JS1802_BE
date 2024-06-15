package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.Response;

public interface OrderService {
    Response createOrder(Integer id, String address, String numberPhone, String cusName);

    Response getAllOrder();

    Response getOrder(Integer id);

    Response cancelOrder(Integer id);

    Response updateOrderStatus(Integer orderId, String newStatus);

    Response getDeliveredOrders(Integer accountID);

    Response getDeliveringOrders(Integer accountID);
}
