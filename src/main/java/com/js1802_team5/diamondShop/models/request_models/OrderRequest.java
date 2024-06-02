package com.js1802_team5.diamondShop.models.request_models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Integer customerId;
    private double totalPrice;
    private String address;
    private Date purchaseDate;
    private Date warrantyStartDate;
    private Date warrantyEndDate;
    private List<OrderDetailRequest> orderDetails;
}
