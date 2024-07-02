package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.SizeRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth/size")
@RequiredArgsConstructor
public class SizeController {

    private final  SizeService sizeService;

    @PostMapping("/create-size")
    @PreAuthorize("hasAuthority('manager:create')")
    public Response createSize(@Valid @RequestBody SizeRequest sizeRequest){
        return sizeService.createSize(sizeRequest);
    }

    @GetMapping("/get-all-size")
    @PreAuthorize("hasAuthority('manager:read')")
    public Response getAllSize(){
        return sizeService.getAllSize();
    }
}
