package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.Response;

import java.util.List;
import java.util.Date;
public interface PromotionService {
    Response addPromotion(String promotionName, String description, float discountPercent, Date startDate, Date endDate,
                          List<String> memberLevels, List<String> types, List<String> productNames);
    Response deletePromotion(Integer promotionId);
}
