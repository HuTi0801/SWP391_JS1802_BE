package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.IDiamondService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/diamond")
@RequiredArgsConstructor
public class DiamondController {

    private final IDiamondService diamondService;

    //create diamond
    @PostMapping("/create-diamond")
    public Response createDiamond(@Valid @RequestBody DiamondRequest diamondRequest) {
        return diamondService.createDiamond(diamondRequest);
    }

    //get all diamond
    @GetMapping("/get-all-diamond")
    public Response getAllDiamond(){
        return diamondService.getAllDiamond();
    }

    //Get A Diamond by id
    @GetMapping("/get-a-diamond-{id}")
    public Response getADiamond(@PathVariable Integer id) {
        return diamondService.getADiamond(id);
    }

    //search diamond
    @PostMapping("/search-diamond")
    public List<Diamond> searchDiamonds(@RequestBody DiamondRequest diamondRequest) {
        return diamondService.searchDiamond(diamondRequest);
    }

    //update diamond
    @PostMapping("/update-diamond-{id}")
    public Response updateDiamond(@PathVariable Integer id, @RequestBody DiamondRequest updateDiamondRequest) {
        return diamondService.updateDiamond(id, updateDiamondRequest);
    }

    //delete diamond
    @PostMapping("/remove-diamond-{id}")
    public Response removeDiamond(@PathVariable Integer id) {
        return diamondService.removeDiamond(id);
    }
}
