package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import com.js1802_team5.diamondShop.models.response_models.AccountResponse;
import com.js1802_team5.diamondShop.models.response_models.CustomerResponse;
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

    List<AccountResponse> getAccountList(Role role, String status);
    AccountResponse getAccountDetails(Integer accountId);

    CustomerResponse getCustomerByAccountId(Integer accountId);

    Response banAccount(Integer id);

    Response unbanAccount(Integer id);
}
