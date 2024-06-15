package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.*;
import com.js1802_team5.diamondShop.services.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepo promotionRepo;
    private final DiamondRepo diamondRepo;
    private final DiamondShellRepo diamondShellRepo;
    private final PromotionDiamondRepo promotionDiamondRepo;
    private final PromotionDiamondShellRepo promotionDiamondShellRepo;

    private String generatePromotionCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        } while (promotionRepo.findByPromotionCode(code).isPresent());
        return code;
    }

    @Override
    public Response addPromotion(String promotionName, String description, float discountPercent, Date startDate, Date endDate, List<String> memberLevels, List<String> types, List<String> productNames) {
        Response response = new Response();

        if (promotionName.split(" ").length > 20) {
            response.setMessage("Promotion name is too long, exceeding 20 words.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return response;
        }

        if (description.length() > 100) {
            response.setMessage("Description is too long, exceeding 100 words.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return response;
        }

        if (discountPercent <= 0 || discountPercent >= 100) {
            response.setMessage("Discount percent must be between 0 and 100.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return response;
        }

        Date today = resetTime(new Date());
        if (startDate.before(today)) {
            response.setMessage("Start date cannot be before today.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return response;
        }

        if (endDate.before(startDate)) {
            response.setMessage("End date must be after start date.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return response;
        }

        Promotion promotion = new Promotion();
        promotion.setPromotionName(promotionName);
        promotion.setPromotionCode(generatePromotionCode());
        promotion.setDescription(description);
        promotion.setDiscountPercent(discountPercent);
        promotion.setStartDate(startDate);
        promotion.setEndDate(endDate);
        promotion.setType(String.join(",", types));
        promotion.setMemberLevelPromotion(String.join(",", memberLevels));

        Promotion savedPromotion = promotionRepo.save(promotion);

        for (String productName : productNames) {
            diamondRepo.findByName(productName).ifPresent(diamond -> savePromotionDiamond(diamond, savedPromotion));
            diamondShellRepo.findByName(productName).ifPresent(diamondShell -> savePromotionDiamondShell(diamondShell, savedPromotion));
        }

        response.setResult(promotion);
        response.setSuccess(true);
        response.setMessage("Promotion added successfully.");
        response.setStatusCode(200);
        return response;
    }

    public Response deletePromotion(Integer promotionId) {
        Response response = new Response();
        Optional<Promotion> optionalPromotion = promotionRepo.findById(promotionId);
        if (optionalPromotion.isEmpty()) {
            response.setMessage("Promotion not found.");
            response.setSuccess(false);
            response.setStatusCode(404); // Not Found
            return response;
        }

        Promotion promotion = optionalPromotion.get();
        try {
            List<PromotionDiamond> promotionDiamonds = promotionDiamondRepo.findByPromotion(promotion);
            promotionDiamondRepo.deleteAll(promotionDiamonds);

            List<PromotionDiamondShell> promotionDiamondShells = promotionDiamondShellRepo.findByPromotion(promotion);
            promotionDiamondShellRepo.deleteAll(promotionDiamondShells);

            promotionRepo.delete(promotion);

            response.setMessage("Promotion deleted successfully.");
            response.setSuccess(true);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setMessage("Failed to delete promotion.");
            response.setSuccess(false);
            response.setStatusCode(500);
        }

        return response;
    }

    private void savePromotionDiamond(Diamond diamond, Promotion promotion) {
        PromotionDiamond promotionDiamond = new PromotionDiamond();
        promotionDiamond.setDiamond(diamond);
        promotionDiamond.setPromotion(promotion);
        promotionDiamondRepo.save(promotionDiamond);
    }

    private void savePromotionDiamondShell(DiamondShell diamondShell, Promotion promotion) {
        PromotionDiamondShell promotionDiamondShell = new PromotionDiamondShell();
        promotionDiamondShell.setDiamondShell(diamondShell);
        promotionDiamondShell.setPromotion(promotion);
        promotionDiamondShellRepo.save(promotionDiamondShell);
    }

    private Date resetTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
