package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.services.IDiamondService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiamondServiceImpl implements IDiamondService {
    @Autowired
    private DiamondRepo diamondRepo;
    @Override
    public Diamond createDiamond(Diamond diamond) {
        Optional<Diamond> existingDiamond = diamondRepo.findByCertificateNumber(diamond.getCertificateNumber());
        if (existingDiamond.isPresent()) {
            return null;
        }
        return diamondRepo.save(diamond);
    }

    @Override
    public List<Diamond> getAllDiamond() {
        return diamondRepo.findAll();
    }

    @Override
    public Diamond getADiamond(Integer id) {
        Optional<Diamond> diamond = diamondRepo.findById(id);
        if (diamond.isPresent()) {
            return diamond.get();
        } else {
            throw new RuntimeException("Diamond not found with id " + id);
        }
    }
}
