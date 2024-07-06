package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.request_models.CustomerRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;

public interface CustomerService {
    Response updateCustomer(int customerId, CustomerRequest customerRequest);
}
