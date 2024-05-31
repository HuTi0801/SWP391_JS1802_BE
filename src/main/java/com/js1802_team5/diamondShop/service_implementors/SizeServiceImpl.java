package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.models.request_models.SizeRequest;
import com.js1802_team5.diamondShop.repositories.SizeRepo;
import com.js1802_team5.diamondShop.services.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepo sizeRepo;

    //create size
    @Override
    public Size createSize(Size size) {
        return sizeRepo.save(size);
    }

    //get all size
    @Override
    public List<Size> getAllSize() {
        return sizeRepo.findAll();
    }

    //convert size to sizeRequest
    @Override
    public SizeRequest toSizeRequest(Size size) {
        var sizeRequest = new SizeRequest();
        sizeRequest.setId(size.getId());
        sizeRequest.setSize(size.getSize());
        return sizeRequest;
    }

    //This function use to mapping from list size to List size request
    @Override
    public List<SizeRequest> toListSizeRequest(List<Size> size) {
        List<SizeRequest> sizeRequest = new ArrayList<>();
        for (Size sizes : size) {
            sizeRequest.add(toSizeRequest(sizes));
        }
        return sizeRequest;
    }

    //convert sizeRequest to size
    @Override
    public Size toSize(SizeRequest sizeRequest) {
        var size = new Size();
        size.setId(sizeRequest.getId());
        size.setSize(sizeRequest.getSize());
        return size;
    }

}
