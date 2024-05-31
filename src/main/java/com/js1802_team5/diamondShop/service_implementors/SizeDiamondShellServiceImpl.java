package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.repositories.SizeDiamondShellRepo;
import com.js1802_team5.diamondShop.services.SizeDiamondShellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeDiamondShellServiceImpl implements SizeDiamondShellService {
    private final SizeDiamondShellRepo sizeDiamondShellRepo;
    @Override
    public List<Integer> getSizesByDiamondShellId(int diamondShellId) {
        return sizeDiamondShellRepo.findSizesByDiamondShellId(diamondShellId);
    }
}
