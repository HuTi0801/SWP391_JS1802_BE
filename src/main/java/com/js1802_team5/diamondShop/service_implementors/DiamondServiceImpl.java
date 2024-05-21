package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.services.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondServiceImpl implements IDiamondService {
    @Autowired
    private DiamondRepo diamondRepo;

    @Override
    public Diamond createDiamond(Diamond diamond) {
        if(diamond != null){
            return diamondRepo.save(diamond);
        }
        return null;
    }

    @Override
    public List<Diamond> getAllDiamond() {
        return diamondRepo.findAll();
    }
}
