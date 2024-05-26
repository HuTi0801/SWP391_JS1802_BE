package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.services.FirebaseStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class FirebaseController {

    private final FirebaseStorageService firebaseStorageService;
    @PostMapping("/upload-image")
    @Operation(summary = "Upload a file", description = "Allows users to upload a file")
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "File to upload", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "multipart/form-data"))
            @RequestParam("file") MultipartFile file)
    {
        try {

            String fileName = firebaseStorageService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
