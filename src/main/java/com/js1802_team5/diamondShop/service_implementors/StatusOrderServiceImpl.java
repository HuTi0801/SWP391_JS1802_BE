package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.StatusOrder;
import com.js1802_team5.diamondShop.models.request_models.StatusOrderRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.StatusOrderRepo;
import com.js1802_team5.diamondShop.services.StatusOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusOrderServiceImpl implements StatusOrderService {
    private final StatusOrderRepo statusOrderRepo;

    @Override
    public Response createStatusOrder(StatusOrderRequest statusOrderRequest) {
        Response response = new Response();
        try {
            Optional<StatusOrder> existingStatusName = statusOrderRepo.findByStatusName(statusOrderRequest.getStatusName());
            if (existingStatusName.isPresent()) {
                response.setSuccess(false);
                response.setMessage("Status name is duplicated!");
                response.setStatusCode(404);
                response.setResult(statusOrderRequest);
            } else {
                response.setMessage("Create status successfully!");
                response.setResult(statusOrderRequest);
                response.setSuccess(true);
                response.setStatusCode(200);
                statusOrderRepo.save(toStatusOrder(statusOrderRequest));
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response getAllStatusOrder() {
        Response response = new Response();
        try {
            var statusOrder = statusOrderRepo.findAll();
            if (statusOrder.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no status!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all status order successfully!");
                response.setResult(toListStatusOrderRequest(statusOrder));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response getAStatusOrder(Integer id) {
        Response response = new Response();
        try {
            var statusOrder = statusOrderRepo.findById(id);
            if (statusOrder.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no status!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get a status order successfully!");
                response.setResult(toStatusOrderRequest(statusOrder.get()));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public StatusOrderRequest toStatusOrderRequest(StatusOrder statusOrder) {
        return StatusOrderRequest.builder()
                .id(statusOrder.getId())
                .statusName(statusOrder.getStatusName())
                .build();
    }

    @Override
    public List<StatusOrderRequest> toListStatusOrderRequest(List<StatusOrder> statusOrderList) {
        List<StatusOrderRequest> statusOrderRequestList = new ArrayList<>();
        for (StatusOrder statusOrder : statusOrderList) {
            statusOrderRequestList.add(toStatusOrderRequest(statusOrder));
        }
        return statusOrderRequestList;
    }

    @Override
    public StatusOrder toStatusOrder(StatusOrderRequest statusOrderRequest) {
        return StatusOrder.builder()
                .id(statusOrderRequest.getId())
                .statusName(statusOrderRequest.getStatusName())
                .build();
    }
}
