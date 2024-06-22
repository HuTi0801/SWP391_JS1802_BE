package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.AccountOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountOrderRepo extends JpaRepository<AccountOrder, Integer> {
    List<AccountOrder> findByAccount_Id(Integer accountId);
    boolean existsByAccount_IdAndOrder_Id(Integer accountId, Integer orderId);

    @Query("SELECT ao FROM AccountOrder ao WHERE ao.account.id IN :accountIds")
    List<AccountOrder> findAllByAccountIds(List<Integer> accountIds);
}
