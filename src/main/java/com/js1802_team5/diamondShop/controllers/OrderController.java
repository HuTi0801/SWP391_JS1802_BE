package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-order")
    public Response createOrder(Integer id, String address, String numberPhone, String cusName) {
        return orderService.createOrder(id, address, numberPhone, cusName);
    }

    @GetMapping("/get-all-orders")
    public Response getAllOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/get-order-{id}")
    public Response getOrder(@PathVariable Integer id) {
        return orderService.getOrder(id);
    }

    @PostMapping("/cancel-order-{id}")
    public Response cancelOrder(@PathVariable Integer id){
        return orderService.cancelOrder(id);
    }

    @PostMapping("/update-status-order-{orderId}-{newStatus}")
    public Response updateStatusOrder(@PathVariable Integer orderId, @RequestParam String newStatus){
        return orderService.updateOrderStatus(orderId, newStatus);
    }
}
