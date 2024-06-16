package com.js1802_team5.diamondShop.mappers;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.response_models.DiamondSearchResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DiamondMapper {
    public static DiamondSearchResponse toResponse(Diamond diamond) {
        return DiamondSearchResponse.builder()
                .id(diamond.getId())
                .name(diamond.getName())
                .origin(diamond.getOrigin())
                .clarity(diamond.getClarity())
                .caratWeight(diamond.getCaratWeight())
                .price(diamond.getPrice())
                .color(diamond.getColor())
                .cut(diamond.getCut())
                .certificateNumber(diamond.getCertificateNumber())
                .quantity(diamond.getQuantity())
                .imageDiamond(diamond.getImageDiamond())
                .statusDiamond(diamond.isStatusDiamond())
                .build();
    }
}
