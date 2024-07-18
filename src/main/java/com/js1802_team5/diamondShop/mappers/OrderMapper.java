package com.js1802_team5.diamondShop.mappers;

import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.models.request_models.OrderDetailRequest;
import com.js1802_team5.diamondShop.models.request_models.OrderRequest;
import com.js1802_team5.diamondShop.models.response_models.DateStatusOrderResponse;
import com.js1802_team5.diamondShop.models.response_models.OrderDetailResponse;
import com.js1802_team5.diamondShop.models.response_models.OrderResponse;
import com.js1802_team5.diamondShop.repositories.CustomerRepo;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.repositories.DiamondShellRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final CustomerRepo customerRepository;
    private final DiamondRepo diamondRepository;
    private final DiamondShellRepo diamondShellRepository;


    public Order toOrder(OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer's is not exist"));

        Order order = Order.builder()
                .address(orderRequest.getAddress())
                .phone(orderRequest.getPhone())
                .cusName(orderRequest.getCusName())
                .totalPrice(orderRequest.getTotalPrice())
                .isCancel(false) // mặc định là false khi tạo mới
                .customer(customer)
                .description(orderRequest.getDescription())
                .build();

        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream()
                .map(detailRequest -> toOrderDetail(detailRequest, order))
                .collect(Collectors.toList());

        order.setOrderDetailList(orderDetails);

        return order;
    }

    public OrderDetail toOrderDetail(OrderDetailRequest detailRequest, Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setQuantity(detailRequest.getQuantity());
        orderDetail.setPrice(detailRequest.getPrice());
        orderDetail.setSize(detailRequest.getSize());

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

    public OrderResponse toOrderResponse(Order order) {
        List<OrderDetailResponse> orderDetails = order.getOrderDetailList().stream()
                .map(this::toOrderDetailResponse)
                .collect(Collectors.toList());

        List<DateStatusOrderResponse> dateStatusOrderResponses = order.getDateStatusOrderList().stream()
                .map(dateStatusOrder -> DateStatusOrderResponse.builder()
                        .dateStatus(dateStatusOrder.getDateStatus())
                        .status(dateStatusOrder.getStatus())
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
//                .orderId(order.getId())
//                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null) // Cập nhật phần này
                .address(order.getAddress())
                .phone(order.getPhone())
                .cusName(order.getCusName())
                .warrantyStartDate(order.getWarrantyStartDate())
                .warrantyEndDate(order.getWarrantyEndDate())
                .totalPrice(order.getTotalPrice())
                .isCancel(order.isCancel())
                .orderDetails(orderDetails)
                .dateStatusOrders(dateStatusOrderResponses)
                .description(order.getDescription())
                .build();
    }

    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .productId(orderDetail.getDiamond() != null ? orderDetail.getDiamond().getId() : orderDetail.getDiamondShell().getId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .size(orderDetail.getSize())
                .diamondId(orderDetail.getDiamond() != null ? "Diamond" : null) // Cập nhật phần này
                .diamondShellId(orderDetail.getDiamondShell() != null ? "Diamond Shell" : null) // Cập nhật phần này
                .build();
    }

    public DateStatusOrderResponse toDateStatusOrderResponse(DateStatusOrder dateStatusOrder) {
        return DateStatusOrderResponse.builder()
                .dateStatus(dateStatusOrder.getDateStatus())
                .status(dateStatusOrder.getStatus())
                .build();
    }

    public List<OrderResponse> toListOrderResponse(List<Order> orders) {
        return orders.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }


}
