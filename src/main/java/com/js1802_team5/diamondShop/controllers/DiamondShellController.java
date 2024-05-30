package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.services.DiamondShellService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/diamond-shell")
@RequiredArgsConstructor
public class DiamondShellController {

    private final DiamondShellService diamondShellService;

    //create diamond shell
    @PostMapping("/create-diamond-shell")
    public ResponseEntity<String> createDiamondShell(@Valid @RequestBody DiamondShellRequest diamondShellRequest){
        var diamondShell = diamondShellService.toDiamond(diamondShellRequest);
        if(diamondShellService.createDiamondShell(diamondShell) != null){
            return ResponseEntity.ok("Diamond shell created successfully");
        }
        return ResponseEntity.status(500).body("Diamond shell create failed");
    }

    //get all diamond shell
    @GetMapping("/get-all-diamond-shell")
    public List<DiamondShellRequest> getAllDiamondShell(){
        return diamondShellService.toListDiamondShellRequest(diamondShellService.getAllDiamondShell());
    }

    //get a diamond shell by id
    @GetMapping("/get-a-diamond-shell-{id}")
    public DiamondShellRequest getADiamondShell(@PathVariable Integer id) {
        return diamondShellService.toDiamondShellRequest(diamondShellService.getADiamondShell(id));
    }

    //Add Size to Diamond Shell
//    @PostMapping("/{diamondShellId}/add-size/{sizeId}")
//    public ResponseEntity<DiamondShell> addSizeToDiamondShell(@PathVariable Integer diamondShellId, @PathVariable Integer sizeId) {
//        DiamondShell updatedDiamondShell = diamondShellService.addSizeToDiamondShell(diamondShellId, sizeId);
//        return ResponseEntity.ok(updatedDiamondShell);
//    }

    //search diamond shell
    @PostMapping("/searchDiamondShell")
    public List<DiamondShell> searchDiamonds(@RequestBody DiamondShellRequest diamondShellRequest) {
        return diamondShellService.searchDiamondShell(diamondShellRequest);
    }
}
