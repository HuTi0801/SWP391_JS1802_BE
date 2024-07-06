package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.CustomerRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/update-info-customer/{customerId}")
    public Response updateCustomer(@PathVariable("customerId") int customerId, @RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(customerId, customerRequest);
    }
}
