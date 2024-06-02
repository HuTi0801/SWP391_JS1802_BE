package com.js1802_team5.diamondShop.mappers;

import com.js1802_team5.diamondShop.models.entity_models.Roles;
import com.js1802_team5.diamondShop.models.request_models.RolesRequest;
import com.js1802_team5.diamondShop.models.response_models.RolesResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RolesMapper {

    public RolesRequest toRolesRequest(Roles roles) {
        return RolesRequest.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .build();
    }

    public List<RolesRequest> toListRolesRequest(List<Roles> rolesList) {
        List<RolesRequest> rolesRequests = new ArrayList<>();
        for (Roles roles : rolesList) {
            rolesRequests.add(toRolesRequest(roles));
        }
        return rolesRequests;
    }

    public Roles toRoles(RolesRequest rolesRequest) {
        return Roles.builder()
                .id(rolesRequest.getId())
                .roleName(rolesRequest.getRoleName())
                .build();
    }

    public RolesResponse toRolesResponse(RolesRequest rolesRequest){
        return RolesResponse.builder()
                .roleName(rolesRequest.getRoleName())
                .build();
    }
}
