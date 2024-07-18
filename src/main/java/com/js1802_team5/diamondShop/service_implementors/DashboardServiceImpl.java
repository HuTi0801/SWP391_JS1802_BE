package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import com.js1802_team5.diamondShop.models.entity_models.AccountOrder;
import com.js1802_team5.diamondShop.models.entity_models.DateStatusOrder;
import com.js1802_team5.diamondShop.models.entity_models.Order;
import com.js1802_team5.diamondShop.models.response_models.OrderRevenueResponse;
import com.js1802_team5.diamondShop.models.response_models.RevenueResponse;
import com.js1802_team5.diamondShop.repositories.AccountOrderRepo;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
import com.js1802_team5.diamondShop.repositories.DateStatusOrderRepo;
import com.js1802_team5.diamondShop.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DateStatusOrderRepo dateStatusOrderRepository;
    private final AccountRepo accountRepo;
    private final AccountOrderRepo accountOrderRepo;

    @Override
    public List<RevenueResponse> getRevenueByYear(int year) {
        List<RevenueResponse> revenueResponses = new ArrayList<>();

        List<DateStatusOrder> dateStatusOrders = dateStatusOrderRepository.findByYear(year);

        // Map to hold the latest date status for each order
        Map<Integer, DateStatusOrder> latestDateStatusByOrder = new HashMap<>();
        for (DateStatusOrder dso : dateStatusOrders) {
            int orderId = dso.getOrder().getId();
            if (!latestDateStatusByOrder.containsKey(orderId) || dso.getDateStatus().after(latestDateStatusByOrder.get(orderId).getDateStatus())) {
                latestDateStatusByOrder.put(orderId, dso);
            }
        }

        // Group orders by month and status
        Map<Integer, Map<String, List<OrderRevenueResponse>>> ordersByMonthAndStatus = new HashMap<>();
        for (DateStatusOrder dso : latestDateStatusByOrder.values()) {
            int month = dso.getDateStatus().getMonth() + 1;
            String status = dso.getStatus();
            Order order = dso.getOrder();
            ordersByMonthAndStatus
                    .computeIfAbsent(month, k -> new HashMap<>())
                    .computeIfAbsent(status, k -> new ArrayList<>())
                    .add(new OrderRevenueResponse(order.getId(), order.getTotalPrice()));
        }
        // Construct RevenueResponse objects
        for (Map.Entry<Integer, Map<String, List<OrderRevenueResponse>>> monthEntry : ordersByMonthAndStatus.entrySet()) {
            int month = monthEntry.getKey();
            for (Map.Entry<String, List<OrderRevenueResponse>> statusEntry : monthEntry.getValue().entrySet()) {
                String status = statusEntry.getKey();
                List<OrderRevenueResponse> orderRevenues = statusEntry.getValue();
                double totalRevenue = orderRevenues.stream().mapToDouble(OrderRevenueResponse::getOrderValue).sum();
                RevenueResponse revenueResponse = new RevenueResponse();
                revenueResponse.setMonth(month);
                revenueResponse.setStatusName(status);
                revenueResponse.setOrderRevenues(orderRevenues);
                revenueResponse.setTotalRevenue(totalRevenue);

                revenueResponses.add(revenueResponse);
            }
        }
        return revenueResponses;
    }

    public Map<String, Object> getPerformance(int year) {
        List<DateStatusOrder> dateStatusOrders = dateStatusOrderRepository.findAllByYearAndStatuses(year);

        Map<Integer, Map<Integer, Long>> confirmedOrdersByMonth = new HashMap<>();
        Map<Integer, Map<Integer, Long>> deliveringOrdersByMonth = new HashMap<>();

        for (DateStatusOrder dso : dateStatusOrders) {
            int month = dso.getDateStatus().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
            String status = dso.getStatus();

            if ("Confirmed".equals(status)) {
                confirmedOrdersByMonth.computeIfAbsent(dso.getOrder().getId(), k -> new HashMap<>())
                        .merge(month, 1L, Long::sum);
            } else if ("Delivering".equals(status)) {
                deliveringOrdersByMonth.computeIfAbsent(dso.getOrder().getId(), k -> new HashMap<>())
                        .merge(month, 1L, Long::sum);
            }
        }

        List<Account> accounts = accountRepo.findAllByRoles();
        List<Integer> accountIds = accounts.stream().map(Account::getId).collect(Collectors.toList());
        List<AccountOrder> accountOrders = accountOrderRepo.findAllByAccountIds(accountIds);

        Map<String, Object> result = new HashMap<>();
        for (Account account : accounts) {
            Map<Integer, Long> monthlyOrderCount = new HashMap<>();
            for (AccountOrder ao : accountOrders) {
                if (ao.getAccount().getId().equals(account.getId())) {
                    int orderId = ao.getOrder().getId();
                    if (account.getRole() == Role.SALE_STAFF) {
                        if (confirmedOrdersByMonth.containsKey(orderId)) {
                            confirmedOrdersByMonth.get(orderId).forEach((month, count) ->
                                    monthlyOrderCount.merge(month, count, Long::sum));
                        }
                    } else if (account.getRole() == Role.DELIVERY_STAFF) {
                        if (deliveringOrdersByMonth.containsKey(orderId)) {
                            deliveringOrdersByMonth.get(orderId).forEach((month, count) ->
                                    monthlyOrderCount.merge(month, count, Long::sum));
                        }
                    }
                }
            }
            Map<String, Object> accountData = new HashMap<>();
            accountData.put("username", account.getUsername());
            accountData.put("role", account.getRole().name());
            accountData.put("monthlyOrderCount", monthlyOrderCount);
            result.put(account.getUsername(), accountData);
        }
        return result;
    }
}
