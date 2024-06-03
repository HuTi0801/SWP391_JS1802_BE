package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.mappers.OrderMapper;
import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.models.request_models.OrderDetailRequest;
import com.js1802_team5.diamondShop.models.request_models.OrderRequest;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;
import com.js1802_team5.diamondShop.models.response_models.OrderResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.*;
import com.js1802_team5.diamondShop.services.CartService;
import com.js1802_team5.diamondShop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepository;
    private final CustomerRepo customerRepository;
    private final CartService cartService;
    private final StatusOrderRepo statusOrderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Response createOrder(Integer customerId, String address) {
        Response response = new Response();
        try {
            // Lấy thông tin customer
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new Exception("Customer is not exist"));

            // Lấy thông tin giỏ hàng của customer
            CartResponse cartResponse = cartService.getCartByCustomerID(customerId);

            // Tạo danh sách OrderDetailRequest từ giỏ hàng
            List<OrderDetailRequest> orderDetailRequests = cartResponse.getItems().stream().map(item -> {
                OrderDetailRequest.OrderDetailRequestBuilder detailRequestBuilder = OrderDetailRequest.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(item.getUnitPrice());

                if (item.getProductType() == ProductType.DIAMOND) {
                    detailRequestBuilder.diamondId(item.getProductId());
                } else if (item.getProductType() == ProductType.DIAMOND_SHELL) {
                    detailRequestBuilder.diamondShellId(item.getProductId());
                }

                return detailRequestBuilder.build();
            }).collect(Collectors.toList());

            // Tạo đối tượng OrderRequest từ giỏ hàng và thông tin khách hàng
            OrderRequest orderRequest = OrderRequest.builder()
                    .customerId(customerId)
                    .address(address)
                    .purchaseDate(new Date()) // Ngày mua hàng là ngày hiện tại
                    .orderDetails(orderDetailRequests)
                    .build();

            // Tính tổng giá từ giỏ hàng
            double totalPrice = cartResponse.getTotalPrice();
            orderRequest.setTotalPrice(totalPrice);

            // Chuyển đổi OrderRequest thành thực thể Order
            Order order = orderMapper.toOrder(orderRequest);

//            // Lấy trạng thái "Confirmed"
//            StatusOrder confirmedStatus = statusOrderRepository.findByStatusName("Confirmed")
//                    .orElseThrow(() -> new Exception("Trạng thái 'Confirmed' không tồn tại"));
//
//            // Tính ngày bắt đầu và kết thúc bảo hành nếu trạng thái là "Confirmed"
//            if (order.getStatusOrder() != null && order.getStatusOrder().getId().equals(confirmedStatus.getId())) {
//                Date warrantyStartDate = new Date();
//                order.setWarrantyStartDate(warrantyStartDate);
//                // Tính ngày kết thúc bảo hành là 3 tháng sau ngày bắt đầu bảo hành
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(warrantyStartDate);
//                calendar.add(Calendar.MONTH, 3);
//                order.setWarrantyEndDate(calendar.getTime());
//            }

            // Lấy trạng thái "Pending"
            StatusOrder pendingStatus = statusOrderRepository.findByStatusName("Pending")
                    .orElseThrow(() -> new Exception("Status 'Pending' is not exist"));

            // Thiết lập trạng thái mặc định là "Pending"
            order.setStatusOrder(pendingStatus);

            // Lưu đối tượng Order vào cơ sở dữ liệu
            orderRepository.save(order);

            OrderResponse orderResponse = orderMapper.toOrderResponse(order);

            // Cấu hình phản hồi thành công
            response.setSuccess(true);
            response.setMessage("Create order successfully!");
            response.setStatusCode(200);
            response.setResult(orderResponse);

        } catch (Exception e) {
            // Cấu hình phản hồi thất bại
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getAllOrder() {
        Response response = new Response();
        try {
            var orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no order!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all orders successfully!");
                response.setResult(orderMapper.toListOrderRequest(orders));
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
    public Response getOrder(Integer id) {
        Response response = new Response();
        try {
            var orders = orderRepository.findById(id);
            if (orders.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no order!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get order successfully!");
                response.setResult(orderMapper.toOrderRequest(orders.get()));
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


}
