package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;

import java.util.List;

public interface IDiamondService {

    Diamond createDiamond(Diamond diamond);

    //getAll Diamond
    List<Diamond> getAllDiamond();

    Diamond getADiamond(Integer id);

    List<Diamond> searchDiamond(DiamondRequest diamondRequest);

}
