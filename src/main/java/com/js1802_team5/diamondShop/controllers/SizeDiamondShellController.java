package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.services.SizeDiamondShellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/size-diamond-shell")
@RequiredArgsConstructor
public class SizeDiamondShellController {
    private final SizeDiamondShellService sizeDiamondShellService;
    @GetMapping("/{diamondShellId}")
    @PreAuthorize("hasAuthority('manager:read') or hasAuthority('customer:read') ")
    public ResponseEntity<List<Integer>> getSizesByDiamondShellId(@PathVariable int diamondShellId) {
        List<Integer> sizes = sizeDiamondShellService.getSizesByDiamondShellId(diamondShellId);
        return ResponseEntity.ok(sizes);
    }
}
