package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.repositories.SizeRepo;
import com.js1802_team5.diamondShop.services.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
