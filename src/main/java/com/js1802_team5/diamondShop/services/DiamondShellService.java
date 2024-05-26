package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;

import java.util.List;

public interface DiamondShellService {
    //create Diamond Shell
    public DiamondShell createDiamondShell(DiamondShell diamondShell);

    //get all Diamond Shell
    public List<DiamondShell> getAllDiamondShell();

    //add size to Diamond Shell
    public DiamondShell addSizeToDiamondShell(Integer diamondShellId, Integer sizeId);

    //Get a diamond shell
    public DiamondShell getADiamondShell(Integer id);
}
