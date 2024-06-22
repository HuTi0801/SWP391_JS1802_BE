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
    private Integer id;
    private Integer customerId;
    private double totalPrice;
    private String address;
    private String phone;
    private String cusName;
//    private Date warrantyStartDate;
//    private Date warrantyEndDate;
    private boolean isCancel;
    private List<OrderDetailRequest> orderDetails;
    private String description;
}
