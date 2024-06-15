package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.Response;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Response login(String username, String password) throws AuthenticationException;

    Response logout(String token);

    Response forgetPassword(String phone, String newPassword);

    Response register(String email, String phoneNumber, String firstName, String lastName, String password);

    List<Map<String, Object>> getActiveSaleStaffWithOrderCounts();

    List<Map<String, Object>> getActiveDeliveryStaffWithOrderCounts();
}
