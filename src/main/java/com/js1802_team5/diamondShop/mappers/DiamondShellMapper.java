package com.js1802_team5.diamondShop.mappers;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondShellResponse;
import com.js1802_team5.diamondShop.models.response_models.SizeDiamondShellResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DiamondShellMapper {
    public DiamondShellRequest toDiamondShellRequest(DiamondShell diamondShell) {
        List<Integer> sizes = diamondShell.getSizeDiamondShellList().stream()
                .map(sizeDiamondShell -> sizeDiamondShell.getSize().getSize())
                .collect(Collectors.toList());

        return DiamondShellRequest.builder()
                .id(diamondShell.getId())
                .name(diamondShell.getName())
                .gender(diamondShell.getGender())
                .quantity(diamondShell.getQuantity())
                .secondaryStoneType(diamondShell.getSecondaryStoneType())
                .material(diamondShell.getMaterial())
                .imageDiamondShell(diamondShell.getImageDiamondShell())
                .price(diamondShell.getPrice())
                .statusDiamondShell(diamondShell.isStatusDiamondShell())
                .sizeIds(sizes)
                .accountId(diamondShell.getAccount().getId())
                .build();
    }

    public List<DiamondShellRequest> toListDiamondShellRequest(List<DiamondShell> diamondShells) {
        List<DiamondShellRequest> diamondShellRequest = new ArrayList<>();
        for (DiamondShell diamondShell : diamondShells) {
            diamondShellRequest.add(toDiamondShellRequest(diamondShell));
        }
        return diamondShellRequest;
    }

    public DiamondShell toDiamond(DiamondShellRequest diamondShellRequest) {
        DiamondShell diamondShell = DiamondShell.builder()
                .id(diamondShellRequest.getId())
                .gender(diamondShellRequest.getGender())
                .quantity(diamondShellRequest.getQuantity())
                .secondaryStoneType(diamondShellRequest.getSecondaryStoneType())
                .material(diamondShellRequest.getMaterial())
                .imageDiamondShell(diamondShellRequest.getImageDiamondShell())
                .price(diamondShellRequest.getPrice())
                .statusDiamondShell(diamondShellRequest.isStatusDiamondShell())
                .build();

        // Gọi phương thức generateName để tự động tạo name cho diamond shell
        diamondShell.generateName();
        return diamondShell;
    }
}
