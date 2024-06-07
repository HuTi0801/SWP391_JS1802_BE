package com.js1802_team5.diamondShop.models.request_models;

import com.js1802_team5.diamondShop.models.entity_models.Order;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailRequest {
    private Integer productId;
    private int quantity;
    private double price;
    private int size;
    private Integer diamondId;
    private Integer diamondShellId;
}
