package com.js1802_team5.diamondShop.mappers;

import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.models.request_models.OrderDetailRequest;
import com.js1802_team5.diamondShop.models.request_models.OrderRequest;
import com.js1802_team5.diamondShop.models.response_models.OrderResponse;
import com.js1802_team5.diamondShop.repositories.CustomerRepo;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.repositories.DiamondShellRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final CustomerRepo customerRepository;
    private final DiamondRepo diamondRepository;
    private final DiamondShellRepo diamondShellRepository;

    public OrderRequest toOrderRequest(Order order) {
        List<OrderDetailRequest> orderDetailRequests = order.getOrderDetailList().stream()
                .map(this::toOrderDetailRequest)
                .collect(Collectors.toList());

        return OrderRequest.builder()
                .purchaseDate(order.getPurchaseDate())
                .address(order.getAddress())
                .customerId(order.getCustomer().getId())
                .warrantyEndDate(order.getWarrantyEndDate())
                .warrantyStartDate(order.getWarrantyStartDate())
                .totalPrice(order.getTotalPrice())
                .orderDetails(orderDetailRequests)
                .build();
    }

    public Order toOrder(OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer's is not exist"));

        Order order = Order.builder()
                .purchaseDate(orderRequest.getPurchaseDate())
                .address(orderRequest.getAddress())
                .warrantyEndDate(orderRequest.getWarrantyEndDate())
                .warrantyStartDate(orderRequest.getWarrantyStartDate())
                .totalPrice(orderRequest.getTotalPrice())
                .customer(customer)
                .build();

        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream()
                .map(detailRequest -> toOrderDetail(detailRequest, order))
                .collect(Collectors.toList());

        order.setOrderDetailList(orderDetails);

        return order;
    }

    public List<OrderRequest> toListOrderRequest(List<Order> orders) {
        List<OrderRequest> orderRequest = new ArrayList<>();
        for (Order order : orders) {
            orderRequest.add(toOrderRequest(order));
        }
        return orderRequest;
    }

    public OrderDetailRequest toOrderDetailRequest(OrderDetail orderDetail) {
        return OrderDetailRequest.builder()
                .productId(orderDetail.getDiamond() != null ? orderDetail.getDiamond().getId() : orderDetail.getDiamondShell().getId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .diamondId(orderDetail.getDiamond() != null ? orderDetail.getDiamond().getId() : null)
                .diamondShellId(orderDetail.getDiamondShell() != null ? orderDetail.getDiamondShell().getId() : null)
                .build();
    }

    public OrderDetail toOrderDetail(OrderDetailRequest detailRequest, Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setQuantity(detailRequest.getQuantity());
        orderDetail.setPrice(detailRequest.getPrice());

        if (detailRequest.getDiamondId() != null) {
            Diamond diamond = diamondRepository.findById(detailRequest.getDiamondId())
                    .orElseThrow(() -> new IllegalArgumentException("Diamond is not exist"));
            orderDetail.setDiamond(diamond);
        }

        if (detailRequest.getDiamondShellId() != null) {
            DiamondShell diamondShell = diamondShellRepository.findById(detailRequest.getDiamondShellId())
                    .orElseThrow(() -> new IllegalArgumentException("DiamondShell is not exist"));
            orderDetail.setDiamondShell(diamondShell);
        }

        return orderDetail;
    }

    //Response
    public OrderResponse toOrderResponse(Order order){
        List<OrderDetailRequest> orderDetailRequests = order.getOrderDetailList().stream()
                .map(this::toOrderDetailRequest)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .purchaseDate(order.getPurchaseDate())
                .address(order.getAddress())
                .customerId(order.getCustomer().getId())
                .warrantyEndDate(order.getWarrantyEndDate())
                .warrantyStartDate(order.getWarrantyStartDate())
                .totalPrice(order.getTotalPrice())
                .orderDetails(orderDetailRequests)
                .build();
    }
}
