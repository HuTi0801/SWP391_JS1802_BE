package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Promotion;
import com.js1802_team5.diamondShop.models.entity_models.PromotionDiamondShell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionDiamondShellRepo extends JpaRepository<PromotionDiamondShell, Integer> {
    List<PromotionDiamondShell> findByPromotion(Promotion promotion);

    List<PromotionDiamondShell> findByPromotionId(Integer promotionId);
}
