package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondShellResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;

import java.util.List;

public interface DiamondShellService {
    //create Diamond Shell
    Response createDiamondShell(DiamondShellRequest diamondShellRequest);

    //get all Diamond Shell
    Response getAllDiamondShell();

    //Get a diamond shell
    Response getADiamondShell(Integer id);

    //update diamond shell
    Response updateDiamondShell(Integer id, DiamondShellRequest updateDiamondShell);

    //delete a diamond shell
    Response removeDiamondShell(Integer id);

    //search diamond shell
    List<DiamondShell> searchDiamondShell(DiamondShellSearchRequest diamondShellSearchRequest);

    DiamondShellResponse convertToDiamondShellResponse(DiamondShell diamondShell);

    //convert diamondShell to diamondShellRequest
    DiamondShellRequest toDiamondShellRequest(DiamondShell diamondShell);

    //This function use to mapping from List Diamond shell to List Diamond shell request
    List<DiamondShellRequest> toListDiamondShellRequest(List<DiamondShell> diamondShells);

    //convert diamondShellRequest to diamondShell
    DiamondShell toDiamond(DiamondShellRequest diamondShellRequest);
}
