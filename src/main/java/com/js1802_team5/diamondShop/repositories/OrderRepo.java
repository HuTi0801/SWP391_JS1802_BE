package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Order;
import com.js1802_team5.diamondShop.models.entity_models.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Optional<Order> findById(Integer id);

    @Query("SELECT o FROM Order o JOIN FETCH o.dateStatusOrderList d WHERE d.status = :status")
    List<Order> findOrdersByStatus(@Param("status") String status);

    Order findTopByOrderByIdDesc();

    List<Order> findByStatusOrder(StatusOrder statusOrder);

    List<Order> findByCustomer_Id(Integer customerId);

    List<Order> findByAccountOrderList_Account_Id(Integer accountId);
}
