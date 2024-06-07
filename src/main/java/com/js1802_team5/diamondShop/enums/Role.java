package com.js1802_team5.diamondShop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.js1802_team5.diamondShop.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {
    GUEST(Collections.emptySet()),

    CUSTOMER(
            Set.of(
                    CUSTOMER_CREATE,
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE
            )
    ),
    SALE_STAFF(
            Set.of(
                SALE_STAFF_CREATE,
                SALE_STAFF_READ,
                SALE_STAFF_UPDATE,
                SALE_STAFF_DELETE
        )
    ),
    DELIVERY_STAFF(
            Set.of(
                    DELIVERY_STAFF_CREATE,
                    DELIVERY_STAFF_READ,
                    DELIVERY_STAFF_UPDATE,
                    DELIVERY_STAFF_DELETE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE
            )
    ),
    ADMIN(
            Set.of(
                ADMIN_CREATE,
                ADMIN_READ,
                ADMIN_UPDATE,
                ADMIN_DELETE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var author = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());

        author.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return author;
    }
}
