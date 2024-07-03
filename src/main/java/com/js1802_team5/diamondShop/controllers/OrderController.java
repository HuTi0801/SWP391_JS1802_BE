package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.TransactionRequest;
import com.js1802_team5.diamondShop.models.response_models.OrderResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-order")
    public Response createOrder(Integer customerId, Integer accountId, String address, String numberPhone, String cusName, String description, TransactionRequest transactionRequest) {
        return orderService.createOrder(customerId, accountId, address, numberPhone, cusName, description, transactionRequest);
    }

    @GetMapping("/get-all-orders")
    public Response getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/get-all-orders-{customerId}")
    public Response getAllOrderByCustomerId(@PathVariable Integer customerId) {
        return orderService.getAllOrderByCustomerId(customerId);
    }

    @GetMapping("/get-all-orders-by-{staffId}")
    public Response getAllOrderByStaffId(@PathVariable Integer staffId) {
        return orderService.getAllOrderByStaffAccount(staffId);
    }

    @GetMapping("/get-order-{orderId}")
    public Response getOrder(@PathVariable Integer orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/get-order-statusName")
    public Response getOrderByStatusName(@RequestParam String statusName) {
        return orderService.getOrderByStatus(statusName);
    }

    @PostMapping("/cancel-order-{id}")
    public Response cancelOrder(@PathVariable Integer id, String description) {
        return orderService.cancelOrder(id, description);
    }

    @PostMapping("/update-order-status-to-confirmed/{orderId}")
    public Response updateOrderStatusToConfirmed(@PathVariable Integer orderId) {
        return orderService.updateOrderStatusToConfirmed(orderId);
    }

    @PostMapping("/update-order-status-from-confirmed/{orderId}")
    public Response updateOrderStatusFromConfirmed(@PathVariable Integer orderId, @RequestParam String newStatus) {
        return orderService.updateOrderStatusFromConfirmed(orderId, newStatus);
    }

    @PostMapping("/update-order-status-to-delivered/{orderId}")
    public Response updateOrderStatusToDelivered(
            @PathVariable Integer orderId,
            @RequestParam boolean isCustomer,
            @RequestParam boolean isDelivery) {
        return orderService.updateOrderStatusToDelivered(orderId, isCustomer, isDelivery);
    }

    @PostMapping("/set-warranty-date/{orderId}")
    public Response setWarrantyDates(@PathVariable Integer orderId) {
        return orderService.setWarrantyDates(orderId);
    }

    @PostMapping("/update-warranty-end-date/{orderId}{endDate}")
    public Response updateWarrantyEndDate(@RequestParam Integer orderId,
                                          @RequestParam("newEndDate") String newEndDateStr) throws ParseException {
        // Chuyển đổi chuỗi thành đối tượng Date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date newEndDate = format.parse(newEndDateStr);
        // Gọi phương thức service với ngày mới
        return orderService.updateWarrantyEndDate(orderId, newEndDate);
    }

    @GetMapping("/view-order-history/{accountId}")
    public Response getDeliveredOrders(@PathVariable Integer accountId) {
        return orderService.getDeliveredOrders(accountId);
    }

    @GetMapping("/view-shipping-order/{accountId}")
    public Response getDeliveringOrders(@PathVariable Integer accountId) {
        return orderService.getDeliveringOrders(accountId);
    }

    @GetMapping("/get-orders-list-by-status")
    public List<OrderResponse> getOrdersByStatus(@RequestParam String status) {
        return orderService.getOrdersByStatus(status);
    }
}
