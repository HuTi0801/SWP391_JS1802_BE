package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.models.request_models.DiamondSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;

import java.util.List;

public interface DiamondService {
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
    List<Diamond> searchDiamond(DiamondSearchRequest diamondSearchRequest);
    List<String> getAllOrigins();
    List<String> getAllClarities();
    List<String> getAllColors();
    List<String> getAllCuts();
    List<Float> getAllCaratWeights();
    List<String> getAllDiamondNames();
}
