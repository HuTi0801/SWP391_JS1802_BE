package com.js1802_team5.diamondShop.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String vnp_PayDate;
    private String vnp_TransactionNo;
    private String vnp_TmnCode;
    private String vnp_SecureHash;
    private String vnp_OrderInfo;
    private String vnp_TxnRef;
    private String vnp_Amount;
    private String vnp_CardType;
    private String vnp_TransactionStatus;
    private String vnp_BankTranNo;
    private String vnp_ResponseCode;
}
