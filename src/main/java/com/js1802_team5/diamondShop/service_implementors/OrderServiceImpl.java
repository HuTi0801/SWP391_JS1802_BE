package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.mappers.OrderMapper;
import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.models.request_models.OrderDetailRequest;
import com.js1802_team5.diamondShop.models.request_models.OrderRequest;
import com.js1802_team5.diamondShop.models.response_models.*;
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
    private final DateStatusOrderRepo dateStatusOrderRepo;
    private final DiamondRepo diamondRepo;
    private final DiamondShellRepo diamondShellRepo;

    @Override
    public Response createOrder(Integer customerId, String address, String numberPhone, String cusName) {
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
                        .size(item.getSize())
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
                    .phone(numberPhone)
                    .cusName(cusName)// Ngày mua hàng là ngày hiện tại
                    .orderDetails(orderDetailRequests)
                    .isCancel(false)
                    .build();

            // Tính tổng giá từ giỏ hàng
            double totalPrice = cartResponse.getTotalPrice();
            orderRequest.setTotalPrice(totalPrice);

            // Chuyển đổi OrderRequest thành thực thể Order
            Order order = orderMapper.toOrder(orderRequest);
            order.setCustomer(customer);

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

            // Khởi tạo danh sách dateStatusOrderList nếu null
            if (order.getDateStatusOrderList() == null) {
                order.setDateStatusOrderList(new ArrayList<>());
            }

            // Lấy trạng thái "Pending"
            StatusOrder pendingStatus = statusOrderRepository.findByStatusName("Pending")
                    .orElseThrow(() -> new Exception("Status 'Pending' is not exist"));

            // Thiết lập trạng thái mặc định là "Pending"
            order.setStatusOrder(pendingStatus);

            // Lưu đối tượng Order vào cơ sở dữ liệu
            orderRepository.save(order);

            // Lưu thông tin order details và cập nhật số lượng sản phẩm
            for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
                if (detailRequest.getDiamondId() != null) {
                    Diamond diamond = diamondRepo.findById(detailRequest.getDiamondId())
                            .orElseThrow(() -> new Exception("Diamond not found"));
                    diamond.setQuantity(diamond.getQuantity() - detailRequest.getQuantity());
                    diamondRepo.save(diamond);
                } else if (detailRequest.getDiamondShellId() != null) {
                    DiamondShell diamondShell = diamondShellRepo.findById(detailRequest.getDiamondShellId())
                            .orElseThrow(() -> new Exception("Diamond shell not found"));
                    diamondShell.setQuantity(diamondShell.getQuantity() - detailRequest.getQuantity());
                    diamondShellRepo.save(diamondShell);
                }
            }

            DateStatusOrder dateStatusOrder = DateStatusOrder.builder()
                    .dateStatus(new Date())
                    .status(pendingStatus.getStatusName())
                    .order(order)
                    .build();
            dateStatusOrderRepo.save(dateStatusOrder);

            // Lấy lại đối tượng Order để đảm bảo tất cả các thông tin đã được cập nhật
            order = orderRepository.findById(order.getId()).orElseThrow(() -> new Exception("Order not found"));

            order.setDateStatusOrderList(dateStatusOrderRepo.findByOrderId(order.getId()));

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
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no order!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all orders successfully!");
                response.setResult(orderMapper.toListOrderResponse(orders));
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
                response.setResult(orderMapper.toOrderResponse(orders.get()));
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
    public Response getOrderByStatus(String status) {
        Response response = new Response();
        try {
            List<Order> orders = orderRepository.findAll(); // Lấy tất cả các đơn hàng trước

            List<OrderResponse> filteredOrders = orders.stream()
                    .filter(order -> {
                        DateStatusOrder latestStatusOrder = order.getDateStatusOrderList().stream()
                                .max(Comparator.comparing(DateStatusOrder::getDateStatus))
                                .orElse(null);
                        return latestStatusOrder != null && latestStatusOrder.getStatus().equals(status);
                    })
                    .map(order -> {
                        DateStatusOrder latestStatusOrder = order.getDateStatusOrderList().stream()
                                .max(Comparator.comparing(DateStatusOrder::getDateStatus))
                                .orElse(null);

                        List<DateStatusOrderResponse> dateStatusOrderResponses = new ArrayList<>();
                        if (latestStatusOrder != null) {
                            dateStatusOrderResponses.add(DateStatusOrderResponse.builder()
                                    .dateStatus(latestStatusOrder.getDateStatus())
                                    .status(latestStatusOrder.getStatus())
                                    .build());
                        }

                        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
                        orderResponse.setDateStatusOrders(dateStatusOrderResponses);
                        return orderResponse;
                    })
                    .collect(Collectors.toList());

            if (filteredOrders.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No orders found with the given status!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get orders successfully!");
                response.setResult(filteredOrders);
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
    public Response cancelOrder(Integer id) {
        Response response = new Response();
        try {
            // Tìm order theo ID
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new Exception("Order is not existed!"));

            // Kiểm tra trạng thái của order
            StatusOrder pendingStatus = statusOrderRepository.findByStatusName("Pending")
                    .orElseThrow(() -> new Exception("Status 'Pending' is not existed!"));

            if (!order.getStatusOrder().getId().equals(pendingStatus.getId())) {
                throw new Exception("Orders can only be canceled when in 'Pending' status!");
            }

            // Thay đổi giá trị isCancel thành true
            order.setCancel(true);

            //Tìm trạng thái Cancel
            StatusOrder cancelStatus = statusOrderRepository.findByStatusName("Cancel")
                    .orElseThrow(() -> new Exception(("Status 'Cancel' is not existed")));
            order.setStatusOrder(cancelStatus);

            //Change date
            DateStatusOrder dateStatusOrder = DateStatusOrder.builder()
                    .order(order)
                    .status(cancelStatus.getStatusName())
                    .dateStatus(new Date())
                    .build();
            dateStatusOrderRepo.save(dateStatusOrder);

            // Lưu order sau khi thay đổi
            orderRepository.save(order);

            OrderResponse orderResponse = orderMapper.toOrderResponse(order);

            response.setSuccess(true);
            response.setMessage("The order was successfully canceled!");
            response.setStatusCode(200);
            response.setResult(orderResponse);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    //update Order status - Sale Staff - Only 2 status:
    @Override
    public Response updateOrderStatus(Integer orderId, String newStatus) {
        Response response = new Response();
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // Kiểm tra trạng thái hiện tại của đơn hàng
            String currentStatus = order.getStatusOrder().getStatusName();

            //Check status "Cancel"
            if (order.getStatusOrder().getStatusName().equals("Cancel")) {
                throw new IllegalStateException("Cannot update order. The order is already canceled!");
            }

            // Xác định thứ tự các trạng thái hợp lệ
            List<String> validStatusOrder = Arrays.asList("Pending", "Confirmed", "Delivering", "Delivered");

            // Kiểm tra xem trạng thái mới có hợp lệ hay không
            if (!validStatusOrder.contains(newStatus)) {
                throw new IllegalArgumentException("Invalid status: " + newStatus);
            }

            // Kiểm tra nếu trạng thái mới không theo thứ tự hợp lệ
            int currentIndex = validStatusOrder.indexOf(currentStatus);
            int newIndex = validStatusOrder.indexOf(newStatus);

            if (newIndex <= currentIndex) {
                throw new IllegalStateException("Cannot update to a previous or the same status.");
            }
            if (newIndex != currentIndex + 1) {
                throw new IllegalStateException("Status must be updated in the correct sequence.");
            }

            // Lấy trạng thái mới và set vào Order
            StatusOrder statusOrder = statusOrderRepository.findByStatusName(newStatus)
                    .orElseThrow(() -> new IllegalArgumentException("Status not found"));
            order.setStatusOrder(statusOrder);

            // Lưu Order vào DB
            order = orderRepository.save(order);

            // Tạo và lưu DateStatusOrder cho trạng thái mới
            DateStatusOrder dateStatusOrder = DateStatusOrder.builder()
                    .dateStatus(new Date())
                    .status(newStatus)
                    .order(order)
                    .build();
            dateStatusOrderRepo.save(dateStatusOrder);

            OrderResponse orderResponse = orderMapper.toOrderResponse(order);
            response.setSuccess(true);
            response.setMessage("Update status order successfully!");
            response.setStatusCode(200);
            response.setResult(orderResponse);

        } catch (IllegalStateException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateOrderStatusToConfirmed(Integer orderId) {
        Response response = new Response();
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // Kiểm tra trạng thái hiện tại của đơn hàng
            String currentStatus = order.getStatusOrder().getStatusName();

            //Check status "Cancel"
            if (order.getStatusOrder().getStatusName().equals("Cancel")) {
                throw new IllegalStateException("Cannot update order. The order is already canceled!");
            }

            // Check if current status is "Pending"
            if (!"Pending".equals(currentStatus)) {
                throw new IllegalStateException("Order status must be 'Pending' to update to 'Confirmed'.");
            }

            // Lấy trạng thái "Confirmed" và set vào Order
            StatusOrder statusOrder = statusOrderRepository.findByStatusName("Confirmed")
                    .orElseThrow(() -> new IllegalArgumentException("Status 'Confirmed' not found"));
            order.setStatusOrder(statusOrder);

            // Lưu Order vào DB
            order = orderRepository.save(order);

            // Tạo và lưu DateStatusOrder cho trạng thái mới
            DateStatusOrder dateStatusOrder = DateStatusOrder.builder()
                    .dateStatus(new Date())
                    .status("Confirmed")
                    .order(order)
                    .build();
            dateStatusOrderRepo.save(dateStatusOrder);

            OrderResponse orderResponse = orderMapper.toOrderResponse(order);
            response.setSuccess(true);
            response.setMessage("Order status updated to 'Confirmed' successfully!");
            response.setStatusCode(200);
            response.setResult(orderResponse);

        } catch (IllegalStateException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateOrderStatusFromConfirmed(Integer orderId, String newStatus) {
        Response response = new Response();
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // Kiểm tra trạng thái hiện tại của đơn hàng
            String currentStatus = order.getStatusOrder().getStatusName();

            //Check status "Cancel"
            if (order.getStatusOrder().getStatusName().equals("Cancel")) {
                throw new IllegalStateException("Cannot update order. The order is already canceled!");
            }

            // Check if current status is "Confirmed"
            if (!"Confirmed".equals(currentStatus) && !"Delivering".equals(currentStatus)) {
                throw new IllegalStateException("Order status must be 'Confirmed' or 'Delivering' to update to the next status.");
            }

            // Xác định thứ tự các trạng thái hợp lệ
            List<String> validStatusOrder = Arrays.asList("Confirmed", "Delivering", "Delivered");

            // Kiểm tra xem trạng thái mới có hợp lệ hay không
            if (!validStatusOrder.contains(newStatus)) {
                throw new IllegalArgumentException("Invalid status: " + newStatus);
            }

            // Kiểm tra nếu trạng thái mới không theo thứ tự hợp lệ
            int currentIndex = validStatusOrder.indexOf(currentStatus);
            int newIndex = validStatusOrder.indexOf(newStatus);

            if (newIndex <= currentIndex) {
                throw new IllegalStateException("Cannot update to a previous or the same status.");
            }
            if (newIndex != currentIndex + 1) {
                throw new IllegalStateException("Status must be updated in the correct sequence.");
            }

            // Lấy trạng thái mới và set vào Order
            StatusOrder statusOrder = statusOrderRepository.findByStatusName(newStatus)
                    .orElseThrow(() -> new IllegalArgumentException("Status not found"));
            order.setStatusOrder(statusOrder);

            // Lưu Order vào DB
            order = orderRepository.save(order);

            // Tạo và lưu DateStatusOrder cho trạng thái mới
            DateStatusOrder dateStatusOrder = DateStatusOrder.builder()
                    .dateStatus(new Date())
                    .status(newStatus)
                    .order(order)
                    .build();
            dateStatusOrderRepo.save(dateStatusOrder);

            OrderResponse orderResponse = orderMapper.toOrderResponse(order);
            response.setSuccess(true);
            response.setMessage("Order status updated to '" + newStatus + "' successfully!");
            response.setStatusCode(200);
            response.setResult(orderResponse);

        } catch (IllegalStateException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

//    @Override
//    public Response updateOrderStatusFromConfirmed(Integer orderId, String newStatus) {
//        Response response = new Response();
//        try {
//            // Lấy thông tin người dùng hiện tại từ SecurityContext
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userRole = authentication.getAuthorities().iterator().next().getAuthority();
//
//            Order order = orderRepository.findById(orderId)
//                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
//
//            // Kiểm tra trạng thái hiện tại của đơn hàng
//            String currentStatus = order.getStatusOrder().getStatusName();
//
//            // Kiểm tra trạng thái "Cancel"
//            if ("Cancel".equals(currentStatus)) {
//                throw new IllegalStateException("Cannot update order. The order is already canceled!");
//            }
//
//            // Kiểm tra nếu trạng thái mới không hợp lệ
//            List<String> validStatusOrder = Arrays.asList("Confirmed", "Delivering", "Delivered");
//            if (!validStatusOrder.contains(newStatus)) {
//                throw new IllegalArgumentException("Invalid status: " + newStatus);
//            }
//
//            // Cập nhật trạng thái dựa trên vai trò người dùng
//            if ("ROLE_CUSTOMER".equalsIgnoreCase(userRole)) {
//                order.setCustomerStatus(newStatus);
//            } else if ("ROLE_DELIVERY_STAFF".equalsIgnoreCase(userRole)) {
//                order.setDeliveryStatus(newStatus);
//            } else {
//                throw new IllegalArgumentException("Invalid user role: " + userRole);
//            }
//
//            // Kiểm tra nếu cả hai trạng thái của customer và delivery đều là "Delivered"
//            if ("Delivered".equals(order.getCustomerStatus()) && "Delivered".equals(order.getDeliveryStatus())) {
//                // Lấy trạng thái mới và set vào Order
//                StatusOrder statusOrder = statusOrderRepository.findByStatusName("Delivered")
//                        .orElseThrow(() -> new IllegalArgumentException("Status not found"));
//                order.setStatusOrder(statusOrder);
//
//                // Tạo và lưu DateStatusOrder cho trạng thái mới
//                DateStatusOrder dateStatusOrder = DateStatusOrder.builder()
//                        .dateStatus(new Date())
//                        .status("Delivered")
//                        .order(order)
//                        .build();
//                dateStatusOrderRepo.save(dateStatusOrder);
//            }
//
//            // Lưu Order vào DB
//            order = orderRepository.save(order);
//
//            OrderResponse orderResponse = orderMapper.toOrderResponse(order);
//            response.setSuccess(true);
//            response.setMessage("Order status updated successfully!");
//            response.setStatusCode(200);
//            response.setResult(orderResponse);
//
//        } catch (IllegalStateException e) {
//            response.setSuccess(false);
//            response.setMessage(e.getMessage());
//            response.setStatusCode(400);
//        } catch (Exception e) {
//            response.setSuccess(false);
//            response.setMessage(e.getMessage());
//            response.setStatusCode(500);
//        }
//        return response;
//    }
}
