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

    //search diamond shell
    List<DiamondShell> searchDiamondShell(DiamondShellRequest diamondShellRequest);

    //convert diamondShell to diamondShellRequest
    DiamondShellRequest toDiamondShellRequest(DiamondShell diamondShell);

    //This function use to mapping from List Diamond shell to List Diamond shell request
    List<DiamondShellRequest> toListDiamondShellRequest(List<DiamondShell> diamondShells);

    //convert diamondShellRequest to diamondShell
    DiamondShell toDiamond(DiamondShellRequest diamondShellRequest);
}
