package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Promotion;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@NonNullApi
public interface PromotionRepo extends JpaRepository<Promotion, Integer> {
    Optional<Promotion> findByPromotionCode(String promotionCode);
    Optional<Promotion> findById(Integer promotionID);
}
