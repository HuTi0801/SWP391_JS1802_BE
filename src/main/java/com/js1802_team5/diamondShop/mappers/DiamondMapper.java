package com.js1802_team5.diamondShop.mappers;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondResponse;
import com.js1802_team5.diamondShop.models.response_models.DiamondSearchResponse;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DiamondMapper {
    private final DiamondRepo diamondRepo;
    private final AccountRepo accountRepo;

    public DiamondRequest toDiamondRequest(Diamond diamond) {
        return DiamondRequest.builder()
                .id(diamond.getId())
                .name(diamond.getName())
                .cut(diamond.getCut())
                .imageDiamond(diamond.getImageDiamond())
                .clarity(diamond.getClarity())
                .color(diamond.getColor())
                .caratWeight(diamond.getCaratWeight())
                .certificateNumber(diamond.getCertificateNumber())
                .quantity(diamond.getQuantity())
                .price(diamond.getPrice())
                .origin(diamond.getOrigin())
                .statusDiamond(diamond.isStatusDiamond())
                .accountId(diamond.getAccount() != null ? diamond.getAccount().getId() : null) // Thêm dòng này
                .build();
    }

    public List<DiamondRequest> toListDiamondRequest(List<Diamond> diamond) {
        List<DiamondRequest> diamondRequest = new ArrayList<>();
        for (Diamond diamonds : diamond) {
            diamondRequest.add(toDiamondRequest(diamonds));
        }
        return diamondRequest;
    }

    public Diamond toDiamond(DiamondRequest diamondRequest) {
        return Diamond.builder()
                .id(diamondRequest.getId())
                .name(diamondRequest.getName())
                .cut(diamondRequest.getCut())
                .imageDiamond(diamondRequest.getImageDiamond())
                .clarity(diamondRequest.getClarity())
                .color(diamondRequest.getColor())
                .caratWeight(diamondRequest.getCaratWeight())
                .certificateNumber(diamondRequest.getCertificateNumber())
                .quantity(diamondRequest.getQuantity())
                .price(diamondRequest.getPrice())
                .origin(diamondRequest.getOrigin())
                .statusDiamond(diamondRequest.isStatusDiamond())
                .build();
    }

    public DiamondResponse convertToDiamondResponse(Diamond diamond) {
        return DiamondResponse.builder()
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
                .accountId(diamond.getAccount() != null ? diamond.getAccount().getId() : null)
                .build();
    }

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
                .accountId(diamond.getAccount() != null ? diamond.getAccount().getId() : null)
                .build();
    }
}
