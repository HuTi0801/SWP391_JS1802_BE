package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Order;
import com.js1802_team5.diamondShop.models.entity_models.StatusOrder;
import com.js1802_team5.diamondShop.models.entity_models.Transaction;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.models.response_models.TransactionResponse;
import com.js1802_team5.diamondShop.repositories.OrderRepo;
import com.js1802_team5.diamondShop.repositories.StatusOrderRepo;
import com.js1802_team5.diamondShop.repositories.TransactionRepo;
import com.js1802_team5.diamondShop.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final OrderRepo orderRepo;
    private final StatusOrderRepo statusOrderRepo;

    @Override
    public TransactionResponse updateOrderInfo(Integer orderId) {
        TransactionResponse response = new TransactionResponse();
        Optional<Transaction> transactionInfo = transactionRepo.findByOrderId(orderId);

        if (transactionInfo.isEmpty()) {
            response.setMessage("Transaction not found.");
            response.setSuccess(false);
            response.setStatusCode(404);
            return response;
        }

        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isEmpty()) {
            response.setMessage("Order is not existed!");
            response.setSuccess(false);
            response.setStatusCode(404);
            return response;
        }

        Optional<StatusOrder> pendingStatus = statusOrderRepo.findByStatusName("Cancel");
        if (pendingStatus.isEmpty()) {
            response.setMessage("Status 'Cancel' is not existed!");
            response.setSuccess(false);
            response.setStatusCode(404);
            return response;
        }

        if (!order.get().getStatusOrder().getId().equals(pendingStatus.get().getId())) {
            response.setMessage("Transaction can only be updated when in 'Cancel' status!");
            response.setSuccess(false);
            response.setStatusCode(400); // Bad Request
            return response;
        }

        Transaction transaction = transactionInfo.get();

        if ("The transaction has been successfully refunded".equals(transaction.getOrderInfo())) {
            response.setMessage("Transaction has already been refunded and cannot be refunded again.");
            response.setSuccess(false);
            response.setStatusCode(400); // Bad Request
            return response;
        }
        transaction.setOrderInfo("The transaction has been successfully refunded");
        transactionRepo.save(transaction);

        response.setMessage("Update order info successfully!");
        response.setVnp_OrderInfo("The transaction has been successfully refunded");
        response.setSuccess(true);
        response.setStatusCode(200);

        return response;
    }
}
