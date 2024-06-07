package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.StatusOrderRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.StatusOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/status-order")
@RequiredArgsConstructor
public class StatusOrderController {
    private final StatusOrderService statusOrderService;

    @PostMapping("/create-status-order")
    public Response createStatusOrder(@Valid @RequestBody StatusOrderRequest statusOrderRequest) {
        return statusOrderService.createStatusOrder(statusOrderRequest);
    }

    //get all diamond
    @GetMapping("/get-all-status-order")
    public Response getAllStatusOrder(){
        return statusOrderService.getAllStatusOrder();
    }

    //Get A Diamond by id
    @GetMapping("/get-a-status-order-{id}")
    public Response getAStatusOrder(@PathVariable Integer id) {
        return statusOrderService.getAStatusOrder(id);
    }
}
