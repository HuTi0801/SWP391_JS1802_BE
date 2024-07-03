package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.request_models.TransactionRequest;
import com.js1802_team5.diamondShop.models.response_models.OrderResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Response createOrder(Integer customerId, Integer accountId, String address, String numberPhone, String cusName, String description, TransactionRequest transactionRequest);

    Response getAllOrder();

    Response getOrder(Integer orderId);

    Response getAllOrderByStaffAccount(Integer staffId);

    Response getAllOrderByCustomerId(Integer customerId);

    Response getOrderByStatus(String status);

    Response cancelOrder(Integer id, String description);

    Response updateOrderStatusToConfirmed(Integer orderId);

    Response updateOrderStatusFromConfirmed(Integer orderId, String newStatus);

    Response updateOrderStatusToDelivered(Integer orderId, boolean isCustomer, boolean isDelivery);

    Response setWarrantyDates(Integer orderId);

    Response updateWarrantyEndDate(Integer orderId, Date newEndDate);

    Response getDeliveredOrders(Integer accountID);

    Response getDeliveringOrders(Integer accountID);

    List<OrderResponse> getOrdersByStatus(String statusName);
}
