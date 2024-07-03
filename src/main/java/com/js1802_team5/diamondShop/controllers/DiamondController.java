package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.request_models.DiamondSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondSearchResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.DiamondService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/diamond")
@RequiredArgsConstructor
public class DiamondController {

    private final DiamondService diamondService;

    @PostMapping("/create-diamond")
    @PreAuthorize("hasAuthority('manager:create')")
    public Response createDiamond(@Valid @RequestBody DiamondRequest diamondRequest) {
        return diamondService.createDiamond(diamondRequest);
    }

    @GetMapping("/get-all-diamond")
    public Response getAllDiamond(){
        return diamondService.getAllDiamond();
    }

    @GetMapping("/get-a-diamond-{id}")
    public Response getADiamond(@PathVariable Integer id) {
        return diamondService.getADiamond(id);
    }

    @PostMapping("/search")
    public ResponseEntity<List<DiamondSearchResponse>> searchDiamonds(@RequestBody DiamondSearchRequest request) {
        List<DiamondSearchResponse> results = diamondService.searchDiamond(request);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/update-diamond-{id}")
    @PreAuthorize("hasAuthority('manager:update')")
    public Response updateDiamond(@PathVariable Integer id, @RequestBody DiamondRequest updateDiamondRequest) {
        return diamondService.updateDiamond(id, updateDiamondRequest);
    }

    @PostMapping("/remove-diamond-{id}")
    @PreAuthorize("hasAuthority('manager:delete')")
    public Response removeDiamond(@PathVariable Integer id) {
        return diamondService.removeDiamond(id);
    }

    @GetMapping("/attributes")
    @PreAuthorize("hasAuthority('manager:read')")
    public ResponseEntity<Response> getDiamondAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("origins", diamondService.getAllOrigins());
        attributes.put("clarities", diamondService.getAllClarities());
        attributes.put("colors", diamondService.getAllColors());
        attributes.put("cuts", diamondService.getAllCuts());
        attributes.put("caratWeights", diamondService.getAllCaratWeights());

        Response response = new Response();
        response.setResult(attributes);
        response.setSuccess(true);
        response.setMessage("Fetched diamond attributes successfully");
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-diamond-names")
    @PreAuthorize("hasAuthority('manager:read')")
    public ResponseEntity<List<String>> getAllDiamondNames() {
        List<String> diamondNames = diamondService.getAllDiamondNames();
        return ResponseEntity.status(HttpStatus.OK).body(diamondNames);
    }
}
