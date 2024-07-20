package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.DateStatusOrder;
import com.js1802_team5.diamondShop.models.entity_models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DateStatusOrderRepo extends JpaRepository<DateStatusOrder, Integer> {
    List<DateStatusOrder> findByOrderId(Integer orderId);

    @Query("SELECT dso FROM DateStatusOrder dso WHERE YEAR(dso.dateStatus) = :year")
    List<DateStatusOrder> findByYear(@Param("year") int year);

    @Query("SELECT dso FROM DateStatusOrder dso WHERE YEAR(dso.dateStatus) = :year AND (dso.status = 'Confirmed' OR dso.status = 'Delivering')")
    List<DateStatusOrder> findAllByYearAndStatuses(int year);

    @Query("SELECT dso FROM DateStatusOrder dso " +
            "WHERE dso.order = :order " +
            "AND dso.status = :statusName " +
            "ORDER BY dso.dateStatus DESC")
    Optional<DateStatusOrder> findFirstByOrderAndStatusOrder_StatusNameOrderByDateStatusDesc(Order order, String statusName);
}
