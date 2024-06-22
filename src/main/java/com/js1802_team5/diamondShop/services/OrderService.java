package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.Response;

public interface OrderService {
    Response createOrder(Integer id, String address, String numberPhone, String cusName, String description);

    Response getAllOrder();

    Response getOrder(Integer id);

    Response getOrderByStatus(String status);

    Response cancelOrder(Integer id, String description);

    Response updateOrderStatus(Integer orderId, String newStatus);

    Response updateOrderStatusToConfirmed(Integer orderId);

    Response updateOrderStatusFromConfirmed(Integer orderId, String newStatus);

    Response getDeliveredOrders(Integer accountID);

    Response getDeliveringOrders(Integer accountID);
}
