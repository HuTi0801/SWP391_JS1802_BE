package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Size;

import java.util.List;

public interface SizeService {
    //add size
    public Size createSize(Size size);

    //get all size
    public List<Size> getAllSize();
}
