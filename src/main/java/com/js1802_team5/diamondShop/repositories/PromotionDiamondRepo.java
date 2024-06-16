package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Promotion;
import com.js1802_team5.diamondShop.models.entity_models.PromotionDiamond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionDiamondRepo extends JpaRepository<PromotionDiamond, Integer> {
    List<PromotionDiamond> findByPromotion(Promotion promotion);
    List<PromotionDiamond> findByPromotionId(Integer promotionId);
}
