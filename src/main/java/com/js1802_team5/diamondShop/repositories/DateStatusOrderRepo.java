package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.DateStatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DateStatusOrderRepo extends JpaRepository<DateStatusOrder, Integer> {
    List<DateStatusOrder> findByOrderId(Integer orderId);
}
