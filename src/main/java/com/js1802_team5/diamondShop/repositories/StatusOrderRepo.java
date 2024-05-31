package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.StatusOrder;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusOrderRepo extends JpaRepository<StatusOrder, Integer> {

    @NonNull
    Optional<StatusOrder> findById(@NonNull  Integer id);

    Optional<StatusOrder> findByStatusName(String statusName);
}
