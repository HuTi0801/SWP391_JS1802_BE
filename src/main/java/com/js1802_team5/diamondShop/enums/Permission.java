package com.js1802_team5.diamondShop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("customer:delete"),

    SALE_STAFF_CREATE("saleStaff:create"),
    SALE_STAFF_READ("saleStaff:read"),
    SALE_STAFF_UPDATE("saleStaff:update"),
    SALE_STAFF_DELETE("saleStaff:delete"),

    DELIVERY_STAFF_CREATE("deliveryStaff:create"),
    DELIVERY_STAFF_READ("deliveryStaff:read"),
    DELIVERY_STAFF_UPDATE("deliveryStaff:update"),
    DELIVERY_STAFF_DELETE("deliveryStaff:delete"),

    MANAGER_CREATE("manager:create"),
    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_DELETE("manager:delete"),

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete");

    @Getter
    private final String permission;
}
