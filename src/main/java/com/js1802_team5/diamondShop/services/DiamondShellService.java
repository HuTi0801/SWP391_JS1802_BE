package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;

import java.util.List;

public interface DiamondShellService {
    //create Diamond Shell
    DiamondShell createDiamondShell(DiamondShell diamondShell);

    //get all Diamond Shell
    List<DiamondShell> getAllDiamondShell();

    //add size to Diamond Shell
    DiamondShell addSizeToDiamondShell(Integer diamondShellId, Integer sizeId);

    //Get a diamond shell
    DiamondShell getADiamondShell(Integer id);

    List<DiamondShell> searchDiamondShell(DiamondShellRequest diamondShellRequest);
}
