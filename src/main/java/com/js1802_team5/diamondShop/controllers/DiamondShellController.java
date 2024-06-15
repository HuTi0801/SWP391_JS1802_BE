package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondShellResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.DiamondShellService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/diamond-shell")
@RequiredArgsConstructor
public class DiamondShellController {

    private final DiamondShellService diamondShellService;

    //Create diamond shell
    @PostMapping("/create-diamond-shell")
    public Response createDiamondShell(@Valid @RequestBody DiamondShellRequest diamondShellRequest){
        return diamondShellService.createDiamondShell(diamondShellRequest);
    }

    //Get all diamond shell
    @GetMapping("/get-all-diamond-shell")
    public Response getAllDiamondShell(){
        return diamondShellService.getAllDiamondShell();
    }

    //Get a diamond shell by id
    @GetMapping("/get-a-diamond-shell-{id}")
    public Response getADiamondShell(@PathVariable Integer id) {
        return diamondShellService.getADiamondShell(id);
    }

    //Search diamond shell
    @PostMapping("/search-diamond-shell")
    public List<DiamondShellResponse> searchDiamondShell(@RequestBody DiamondShellSearchRequest diamondShellSearchRequest) {
        List<DiamondShell> diamondShells = diamondShellService.searchDiamondShell(diamondShellSearchRequest);
        return diamondShells.stream()
                .map(diamondShellService::convertToDiamondShellResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/update-diamond-shell-{id}")
    public Response updateDiamondShell(@PathVariable Integer id, @RequestBody DiamondShellRequest updateDiamondShellRequest) {
        return diamondShellService.updateDiamondShell(id, updateDiamondShellRequest);
    }

    //Delete diamondShell
    @PostMapping("/remove-diamond-shell-{id}")
    public Response removeDiamondShell(@PathVariable Integer id) {
        return diamondShellService.removeDiamondShell(id);
    }

    @GetMapping("/attributes")
    public ResponseEntity<Response> getDiamondShellAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("materials", diamondShellService.getAllMaterials());
        attributes.put("secondaryStoneTypes", diamondShellService.getAllSecondaryStoneTypes());

        Response response = new Response();
        response.setResult(attributes);
        response.setSuccess(true);
        response.setMessage("Fetched diamond shell attributes successfully");
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-diamond-shell-names")
    public ResponseEntity<List<String>> getAllDiamondShellNames() {
        List<String> diamondShellNames = diamondShellService.getAllDiamondShellNames();
        return ResponseEntity.status(HttpStatus.OK).body(diamondShellNames);
    }
}
