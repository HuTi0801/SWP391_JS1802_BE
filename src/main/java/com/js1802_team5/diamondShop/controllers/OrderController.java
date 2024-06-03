package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-order")
    public Response createOrder(Integer id, String address) {
        return orderService.createOrder(id, address);
    }

    @GetMapping("/get-all-orders")
    public Response getAllOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/get-order-{id}")
    public Response getOrder(@PathVariable Integer id) {
        return orderService.getOrder(id);
    }
}
