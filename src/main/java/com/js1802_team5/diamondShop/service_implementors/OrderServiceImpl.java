package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.MemberLevel;
import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.mappers.OrderMapper;
import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.models.request_models.OrderDetailRequest;
import com.js1802_team5.diamondShop.models.request_models.OrderRequest;
import com.js1802_team5.diamondShop.models.request_models.TransactionRequest;
import com.js1802_team5.diamondShop.models.response_models.*;
import com.js1802_team5.diamondShop.repositories.*;
import com.js1802_team5.diamondShop.services.CartService;
import com.js1802_team5.diamondShop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final AccountOrderRepo accountOrderRepo;
    private final TransactionRepo transactionRepo;
    private final AccountRepo accountRepo;
    @Override
    public Response createOrder(Integer customerId, Integer accountId, String address, String numberPhone, String cusName, String description, TransactionRequest transactionRequest) {
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
                    .description(description)
                    .build();

            // Tính tổng giá từ giỏ hàng
            double totalPrice = cartResponse.getTotalPrice();
            orderRequest.setTotalPrice(totalPrice);

            // Chuyển đổi OrderRequest thành thực thể Order
            Order order = orderMapper.toOrder(orderRequest);
            order.setCustomer(customer);

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

            // Cập nhật điểm khách hàng
            int newPoints = (int) (customer.getPoint() + (totalPrice / 1000000));
            customer.setPoint(newPoints);

            // Cập nhật hạng thành viên dựa trên điểm
            if (newPoints >= 5000) {
                customer.setMemberLevel(String.valueOf(MemberLevel.PRIVATE));
            } else if (newPoints >= 1000) {
                customer.setMemberLevel(String.valueOf(MemberLevel.DIAMOND));
            } else if (newPoints >= 400) {
                customer.setMemberLevel(String.valueOf(MemberLevel.PLATINUM));
            } else if (newPoints >= 100) {
                customer.setMemberLevel(String.valueOf(MemberLevel.GOLD));
            } else {
                customer.setMemberLevel(String.valueOf(MemberLevel.SILVER));
            }

            // Lưu thông tin khách hàng cập nhật vào cơ sở dữ liệu
            customerRepository.save(customer);

            // Tạo một giao dịch mới và lưu vào cơ sở dữ liệu
            Transaction transaction = new Transaction();
            transaction.setPayDate(transactionRequest.getVnp_PayDate());
            transaction.setTransactionNo(transactionRequest.getVnp_TransactionNo());
            transaction.setTmnCode(transactionRequest.getVnp_TmnCode());
            transaction.setSecureHash(transactionRequest.getVnp_SecureHash());
            transaction.setOrderInfo(transactionRequest.getVnp_OrderInfo());
            transaction.setTxnRef(transactionRequest.getVnp_TxnRef());
            transaction.setAmount(transactionRequest.getVnp_Amount());
            transaction.setCardType(transactionRequest.getVnp_CardType());
            transaction.setTransactionStatus(transactionRequest.getVnp_TransactionStatus());
            transaction.setBankTranNo(transactionRequest.getVnp_BankTranNo());
            transaction.setResponseCode(transactionRequest.getVnp_ResponseCode());
            transaction.setOrder(order); // Liên kết giao dịch với đơn hàng mới nhất
            transactionRepo.save(transaction);

            //Save information into AccountOrder table
            Account account = accountRepo.findById(accountId)
                    .orElseThrow(() -> new Exception("Account not found"));
            AccountOrder accountOrder = new AccountOrder();
            accountOrder.setAccount(account);
            accountOrder.setOrder(order);
            accountOrderRepo.save(accountOrder);

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
    public Response cancelOrder(Integer id, String description) {
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

            // Lấy danh sách các chi tiết đơn hàng của order để hoàn trả số lượng sản phẩm
            List<OrderDetail> orderDetails = order.getOrderDetailList();

            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.getDiamond() != null) {
                    Diamond diamond = diamondRepo.findById(orderDetail.getDiamond().getId())
                            .orElseThrow(() -> new Exception("Diamond not found"));
                    diamond.setQuantity(diamond.getQuantity() + orderDetail.getQuantity());
                    diamondRepo.save(diamond);
                } else if (orderDetail.getDiamondShell() != null) {
                    DiamondShell diamondShell = diamondShellRepo.findById(orderDetail.getDiamondShell().getId())
                            .orElseThrow(() -> new Exception("Diamond shell not found"));
                    diamondShell.setQuantity(diamondShell.getQuantity() + orderDetail.getQuantity());
                    diamondShellRepo.save(diamondShell);
                }
            }

            // Thay đổi giá trị isCancel thành true
            order.setCancel(true);
            order.setDescription(description);

            // Tìm trạng thái Cancel
            StatusOrder cancelStatus = statusOrderRepository.findByStatusName("Cancel")
                    .orElseThrow(() -> new Exception("Status 'Cancel' is not existed"));
            order.setStatusOrder(cancelStatus);

            // Change date
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

    //update status from Pending to Confirmed
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

    //Update status from Confirmed to Delivering
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


    //Update status from Delivering to Delivred (Customer + Delivery staff)
    public Response updateOrderStatusToDelivered(Integer orderId, boolean isCustomer, boolean isDelivery) {
        Response response = new Response();

        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // Kiểm tra trạng thái hiện tại của đơn hàng
            String currentStatus = order.getStatusOrder().getStatusName();

            // Check if current status is "Delivering"
            if (!"Delivering".equals(currentStatus)) {
                throw new IllegalStateException("Order status must be 'Delivering' to update to 'Delivered'.");
            }

            // Update the flags based on the input
            if (isCustomer) {
                order.setCustomerDelivered(true);
            }
            if (isDelivery) {
                order.setDeliveryDelivered(true);
            }

            // Check if both conditions are met
            if (order.isCustomerDelivered() && order.isDeliveryDelivered()) {
                // Lấy trạng thái "Delivered"
                StatusOrder deliveredStatus = statusOrderRepository.findByStatusName("Delivered")
                        .orElseThrow(() -> new IllegalArgumentException("Status 'Delivered' not found"));

                // Cập nhật trạng thái đơn hàng
                order.setStatusOrder(deliveredStatus);

                // Tạo và lưu DateStatusOrder cho trạng thái mới
                DateStatusOrder dateStatusOrder = DateStatusOrder.builder()
                        .dateStatus(new Date())
                        .status("Delivered")
                        .order(order)
                        .build();
                dateStatusOrderRepo.save(dateStatusOrder);

                response.setSuccess(true);
                response.setMessage("Order status updated to 'Delivered' successfully!");
                response.setStatusCode(200);
                response.setResult(orderMapper.toOrderResponse(order));
            } else {
                // Save the updated order without changing the status
                orderRepository.save(order);

                response.setSuccess(false);
                response.setMessage("Both customer and delivery staff must confirm to update to 'Delivered'.");
                response.setStatusCode(400);
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to update order status to 'Delivered'.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response setWarrantyDates(Integer orderId) {
        Response response = new Response();
        try {
            // Tìm đơn hàng theo ID
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // Kiểm tra trạng thái hiện tại của đơn hàng
            String currentStatus = order.getStatusOrder().getStatusName();

            // Nếu trạng thái không phải là "Confirmed", không làm gì và trả về lỗi
            if (!"Confirmed".equals(currentStatus)) {
                throw new IllegalStateException("Order must be in Confirmed status to set warranty dates.");
            }

            // Lấy ngày khi đơn hàng được xác nhận từ DateStatusOrder
            DateStatusOrder dateStatusOrder = dateStatusOrderRepo.findFirstByOrderAndStatusOrder_StatusNameOrderByDateStatusDesc(order, "Confirmed")
                    .orElseThrow(() -> new IllegalStateException("Confirmed date not found for the order."));

            Date confirmedDate = dateStatusOrder.getDateStatus();

            // Thiết lập ngày bắt đầu bảo hành là ngày khi đơn hàng được xác nhận
            order.setWarrantyStartDate(confirmedDate);

            // Thiết lập ngày kết thúc bảo hành là 6 tháng sau ngày bắt đầu
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(confirmedDate);
            calendar.add(Calendar.MONTH, 6);
            Date endDate = calendar.getTime();
            order.setWarrantyEndDate(endDate);

            // Lưu Order vào DB
            order = orderRepository.save(order);

            // Tạo OrderResponse và set vào response
            OrderResponse orderResponse = orderMapper.toOrderResponse(order);
            response.setSuccess(true);
            response.setMessage("Warranty dates set successfully!");
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

    @Transactional
    @Override
    public Response updateWarrantyEndDate(Integer orderId, Date newEndDate) {
        Response response = new Response();
        try {
            // Tìm đơn hàng theo ID
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // Kiểm tra trạng thái hiện tại của đơn hàng
            String currentStatus = order.getStatusOrder().getStatusName();

            // Nếu trạng thái không phải là "Delivered", không làm gì và trả về lỗi
            if (!"Delivered".equals(currentStatus)) {
                throw new IllegalStateException("Order must be in Delivered status to extend warranty.");
            }

            // Lấy ngày bắt đầu và kết thúc bảo hành hiện tại
            Date currentStartDate = order.getWarrantyStartDate();
            Date currentEndDate = order.getWarrantyEndDate();

            // Kiểm tra ngày kết thúc bảo hành mới
            if (newEndDate.before(currentStartDate)) {
                throw new IllegalArgumentException("New end date must be after the start date.");
            }
            if (newEndDate.before(currentEndDate)) {
                throw new IllegalArgumentException("New end date must be after the current end date.");
            }

            // Cập nhật ngày kết thúc bảo hành
            order.setWarrantyEndDate(newEndDate);

            // Lưu Order vào DB
            order = orderRepository.save(order);

            // Tạo OrderResponse và set vào response
            OrderResponse orderResponse = orderMapper.toOrderResponse(order);
            response.setSuccess(true);
            response.setMessage("Warranty end date extended successfully!");
            response.setStatusCode(200);
            response.setResult(orderResponse);

        } catch (IllegalStateException | IllegalArgumentException e) {
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

    public Response getDeliveredOrders(Integer accountID) {
        Optional<StatusOrder> deliveredStatusOpt = statusOrderRepository.findByStatusName("Delivered");
        if (deliveredStatusOpt.isPresent()) {
            StatusOrder deliveredStatus = deliveredStatusOpt.get();
            List<OrderResponse> orders = accountOrderRepo.findByAccount_Id(accountID).stream()
                    .map(AccountOrder::getOrder)
                    .filter(order -> order.getStatusOrder().equals(deliveredStatus))
                    .map(orderMapper::toOrderResponse)
                    .collect(Collectors.toList());
            return new Response(orders, true, "Successfully retrieved delivered orders", HttpStatus.OK.value());
        } else {
            return new Response(null, false, "Delivered status not found", HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public Response getDeliveringOrders(Integer accountID) {
        Optional<StatusOrder> deliveringStatusOpt = statusOrderRepository.findByStatusName("Delivering");
        if (deliveringStatusOpt.isPresent()) {
            StatusOrder deliveringStatus = deliveringStatusOpt.get();
            List<OrderResponse> orders = accountOrderRepo.findByAccount_Id(accountID).stream()
                    .map(AccountOrder::getOrder)
                    .filter(order -> order.getStatusOrder().equals(deliveringStatus))
                    .map(orderMapper::toOrderResponse)
                    .collect(Collectors.toList());
            return new Response(orders, true, "Successfully retrieved delivering orders", HttpStatus.OK.value());
        } else {
            return new Response(null, false, "Delivering status not found", HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(String statusName) {
        Optional<StatusOrder> optionalStatusOrder = statusOrderRepository.findByStatusName(statusName);
        if (optionalStatusOrder.isPresent()) {
            StatusOrder statusOrder = optionalStatusOrder.get();
            List<Order> orders = orderRepository.findByStatusOrder(statusOrder);
            return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
        } else {
            throw new RuntimeException("Status not found");
        }
    }
}
