package com.js1802_team5.diamondShop.models.response_models;

import com.js1802_team5.diamondShop.models.entity_models.PromotionDiamond;
import com.js1802_team5.diamondShop.models.entity_models.PromotionDiamondShell;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionResponse {
    private Integer id;
    private String promotionCode;
    private String promotionName;
    private Date startDate;
    private Date endDate;
    private String description;
    private String type;
    private String memberLevelPromotion;
    private float discountPercent;
    private List<String> promotionDiamondShellNameList;
    private List<String> promotionDiamondNameList;
}
