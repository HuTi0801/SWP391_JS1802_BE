package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.models.request_models.SizeRequest;

import java.util.List;

public interface SizeService {
    //add size
    public Size createSize(Size size);

    //get all size
    public List<Size> getAllSize();

    //convert size to sizeRequest
    SizeRequest toSizeRequest(Size size);

    //This function use to mapping from list size to List size request
    List<SizeRequest> toListSizeRequest(List<Size> size);

    //convert sizeRequest to size
    Size toSize(SizeRequest sizeRequest);
}
