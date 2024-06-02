package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.mappers.RolesMapper;
import com.js1802_team5.diamondShop.models.entity_models.Roles;
import com.js1802_team5.diamondShop.models.request_models.RolesRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.RolesRepo;
import com.js1802_team5.diamondShop.services.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RolesRepo rolesRepo;
    private final RolesMapper rolesMapper;
    @Override
    public Response createRoles(RolesRequest rolesRequest) {
        Response response = new Response();
        try {

            Optional<Roles> existingRoles = rolesRepo.findByRoleName(rolesRequest.getRoleName());
            if (existingRoles.isPresent()) {
                response.setSuccess(false);
                response.setMessage("Role is duplicated!");
                response.setStatusCode(404);
                response.setResult(rolesRequest);
            } else {
                response.setMessage("Create diamonds successfully!");
                response.setResult(rolesMapper.toRolesResponse(rolesRequest));
                response.setSuccess(true);
                response.setStatusCode(200);
                rolesRepo.save(rolesMapper.toRoles(rolesRequest));
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response getAllRoles() {
        Response response = new Response();
        try {
            var roles = rolesRepo.findAll();
            if (roles.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no roles!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all roles successfully!");
                response.setResult(rolesMapper.toListRolesRequest(roles));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response getARoles(Integer id) {
        Response response = new Response();
        try {
            var roles = rolesRepo.findById(id);
            if (roles.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no role!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get role successfully!");
                response.setResult(rolesMapper.toRolesRequest(roles.get()));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }
}
