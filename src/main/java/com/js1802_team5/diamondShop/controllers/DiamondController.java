package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.services.FirebaseStorageService;
import com.js1802_team5.diamondShop.services.IDiamondService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/diamond")
@RequiredArgsConstructor
public class DiamondController {
    @Autowired
    public IDiamondService diamondService;

    @Autowired
    public FirebaseStorageService firebaseStorageService;

    @GetMapping("/test-api")
    public String testAPI(){
        return "Hello";
    }

    //API create Diamond
    @PostMapping("/create-diamond")
    public Diamond createDiamond(@RequestBody Diamond diamond){
        return diamondService.createDiamond(diamond);
    }

    @GetMapping("get-all-diamond")
    public List<Diamond> getAllDiamond(){
        return diamondService.getAllDiamond();
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = firebaseStorageService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
