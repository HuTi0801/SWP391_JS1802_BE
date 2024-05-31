package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.StatusOrder;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.models.request_models.StatusOrderRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;

import java.util.List;

public interface StatusOrderService {
    //create status order
    Response createStatusOrder(StatusOrderRequest statusOrderRequest);

    //getAll Status order
    Response getAllStatusOrder();

    //get a status order
    Response getAStatusOrder(Integer id);

    //convert diamondRequest to diamond
    StatusOrderRequest toStatusOrderRequest(StatusOrder statusOrder);

    //get list from diamond to diamondRequest
    List<StatusOrderRequest> toListStatusOrderRequest(List<StatusOrder> statusOrderList);

    //convert diamond to diamondRequest
    StatusOrder toStatusOrder(StatusOrderRequest statusOrderRequest);
}
