package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;

import java.util.List;

public interface IDiamondService {
    //create diamond
    public Diamond createDiamond(Diamond diamond);

    //getAll Diamond
    public List<Diamond> getAllDiamond();
}
