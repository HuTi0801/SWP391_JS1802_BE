package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.SizeRequest;
import com.js1802_team5.diamondShop.services.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/size")
@RequiredArgsConstructor
public class SizeController {

    private final  SizeService sizeService;

    //create size
    @PostMapping("/create-size")
    public ResponseEntity<String> createSize(@Valid @RequestBody SizeRequest sizeRequest){
        var size = sizeService.toSize(sizeRequest);
        if(sizeService.createSize(size) != null){
            return ResponseEntity.ok("Size created successfully");
        }
        return ResponseEntity.status(500).body("Size create failed");
    }

    //get All size
    @GetMapping("/get-all-size")
    public List<SizeRequest> getAllSize(){
        return sizeService.toListSizeRequest(sizeService.getAllSize());
    }

}
