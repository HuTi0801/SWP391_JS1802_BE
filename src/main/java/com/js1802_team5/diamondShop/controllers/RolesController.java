package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.models.request_models.RolesRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.RolesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RolesService rolesService;

    @PostMapping("/create-roles")
    public Response createRoles(@Valid @RequestBody RolesRequest rolesRequest) {
        return rolesService.createRoles(rolesRequest);
    }

    //get all diamond
    @GetMapping("/get-all-roles")
    public Response getAllRoles(){
        return rolesService.getAllRoles();
    }

    //Get A Diamond by id
    @GetMapping("/get-a-role-{id}")
    public Response getARole(@PathVariable Integer id) {
        return rolesService.getARoles(id);
    }
}
