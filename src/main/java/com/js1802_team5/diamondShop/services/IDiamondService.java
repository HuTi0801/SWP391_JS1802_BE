package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;

import java.util.List;

public interface IDiamondService {

    //create diamond
    Response createDiamond(DiamondRequest diamondRequest);

    //getAll Diamond
    Response getAllDiamond();

    //get a diamond by id
    Response getADiamond(Integer id);

    //update diamond
    Response updateDiamond(Integer id, DiamondRequest updateDiamond);

    //delete diamond
    Response removeDiamond(Integer id);

    //search diamond
    List<Diamond> searchDiamond(DiamondRequest diamondRequest);

    //convert diamondRequest to diamond
    DiamondRequest toDiamondRequest(Diamond diamond);

    //get list from diamond to diamondRequest
    List<DiamondRequest> toListDiamondRequest(List<Diamond> diamond);

    //convert diamond to diamondRequest
    Diamond toDiamond(DiamondRequest diamondRequest);
}
