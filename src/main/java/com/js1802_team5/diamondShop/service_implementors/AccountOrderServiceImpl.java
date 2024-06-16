package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Account;
import com.js1802_team5.diamondShop.models.entity_models.AccountOrder;
import com.js1802_team5.diamondShop.models.entity_models.Order;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.AccountOrderRepo;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
import com.js1802_team5.diamondShop.repositories.OrderRepo;
import com.js1802_team5.diamondShop.services.AccountOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountOrderServiceImpl implements AccountOrderService {
    private final AccountOrderRepo accountOrderRepo;
    private final OrderRepo orderRepo;
    private final AccountRepo accountRepo;
    public Response assignStaffToOrder(Integer accountId, Integer orderId) {
        try {
            if (accountOrderRepo.existsByAccount_IdAndOrder_Id(accountId, orderId)) {
                return new Response(null, false, "Staff member is already assigned to this order", 400);
            }

            Optional<Account> optionalAccount = accountRepo.findById(accountId);
            if (optionalAccount.isEmpty()) {
                return new Response(null, false, "Account not found with ID = " + accountId, 404);
            }
            Account account = optionalAccount.get();

            Optional<Order> optionalOrder = orderRepo.findById(orderId);
            if (optionalOrder.isEmpty()) {
                return new Response(null, false, "Order not found with ID = " + orderId, 404);
            }
            Order order = optionalOrder.get();

            AccountOrder accountOrder = new AccountOrder();
            accountOrder.setAccount(account);
            accountOrder.setOrder(order);

            accountOrderRepo.save(accountOrder);

            return new Response(null, true, "Staff assigned successfully to the order", 200);
        } catch (Exception e) {
            return new Response(null, false, e.getMessage(), 500);
        }
    }
}
