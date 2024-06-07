package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.SizeRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth/size")
@RequiredArgsConstructor
public class SizeController {

    private final  SizeService sizeService;

    //create size
    @PostMapping("/create-size")
    public Response createSize(@Valid @RequestBody SizeRequest sizeRequest){
        return sizeService.createSize(sizeRequest);
    }

    //get All size
    @GetMapping("/get-all-size")
    public Response getAllSize(){
        return sizeService.getAllSize();
    }

}
