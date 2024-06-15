package com.js1802_team5.diamondShop.models.response_models;

import com.js1802_team5.diamondShop.models.request_models.OrderDetailRequest;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Integer orderId;
    private Integer customerId;
    private double totalPrice;
    private String address;
    private String phone;
    private String cusName;
    private Date warrantyStartDate;
    private Date warrantyEndDate;
    private boolean isCancel;
    private List<OrderDetailResponse> orderDetails;
    private List<DateStatusOrderResponse> dateStatusOrders;
}
