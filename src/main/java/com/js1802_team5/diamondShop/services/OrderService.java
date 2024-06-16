package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Customer;
import com.js1802_team5.diamondShop.models.entity_models.Order;
import com.js1802_team5.diamondShop.models.request_models.OrderRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;

public interface OrderService {
    Response createOrder(Integer id, String address, String numberPhone, String cusName);

    Response getAllOrder();

    Response getOrder(Integer id);

    Response getOrderByStatus(String status);

    Response cancelOrder(Integer id);

    Response updateOrderStatus(Integer orderId, String newStatus);

    Response updateOrderStatusToConfirmed(Integer orderId);

    Response updateOrderStatusFromConfirmed(Integer orderId, String newStatus);
}
