package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;
    @PostMapping("/add")
    public ResponseEntity<Response> addPromotion(@RequestParam String promotionName,
                                                 @RequestParam String description,
                                                 @RequestParam float discountPercent,
                                                 @RequestParam String endDate,
                                                 @RequestParam List<String> memberLevels,
                                                 @RequestParam String startDate,
                                                 @RequestParam List<String> types,
                                                 @RequestParam List<String> productNames) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date startDateParsed, endDateParsed;
        try {
            startDateParsed = formatter.parse(startDate);
            endDateParsed = formatter.parse(endDate);
        } catch (ParseException e) {
            Response response = new Response();
            response.setMessage("Invalid date format.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Response response = promotionService.addPromotion(promotionName, description, discountPercent, startDateParsed, endDateParsed, memberLevels, types, productNames);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Response> deletePromotion(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(promotionService.deletePromotion(id));
    }

    @GetMapping("/get-promotions-list")
    public Response getPromotionList() {
        return promotionService.getPromotionList();
    }

    @GetMapping("/view-promotion/{promotionId}")
    public Response getPromotionDetails(@PathVariable Integer promotionId) {
        return promotionService.getPromotionDetails(promotionId);
    }
}
