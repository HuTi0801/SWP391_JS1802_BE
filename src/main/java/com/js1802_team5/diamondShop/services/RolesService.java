package com.js1802_team5.diamondShop.services;


import com.js1802_team5.diamondShop.models.request_models.RolesRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;

public interface RolesService {
    Response createRoles(RolesRequest rolesRequest);

    Response getAllRoles();

    Response getARoles(Integer id);
}
