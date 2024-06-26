package com.js1802_team5.diamondShop.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`Transaction`")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String payDate;
    private String transactionNo;
    private String tmnCode;
    private String secureHash;
    private String orderInfo;
    private String txnRef;
    private String amount;
    private String cardType;
    private String transactionStatus;
    private String bankTranNo;
    private String responseCode;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
