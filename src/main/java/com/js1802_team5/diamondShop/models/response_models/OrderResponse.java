package com.js1802_team5.diamondShop.models.response_models;

import com.js1802_team5.diamondShop.models.request_models.OrderDetailRequest;
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
    private Integer customerId;
    private double totalPrice;
    private String address;
    private Date purchaseDate;
    private Date warrantyStartDate;
    private Date warrantyEndDate;
    private List<OrderDetailRequest> orderDetails;
}
