package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.response_models.AccountResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {
        Response response = accountService.login(username, password);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/logout")
    //Bearer <token>
    public ResponseEntity<Response> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            Response response = Response.builder()
                    .result(null)
                    .isSuccess(false)
                    .message("Missing or empty Authorization header")
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Response response = accountService.logout(token);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Response> forgetPassword(@RequestParam("phone") String phone,
                                                   @RequestParam("newPassword") String newPassword) {
        Response response = accountService.forgetPassword(phone, newPassword);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestParam("email") String email,
                                             @RequestParam("phone_number") String phoneNumber,
                                             @RequestParam("first_name") String firstName,
                                             @RequestParam("last_name") String lastName,
                                             @RequestParam("password") String password) {
        Response response = accountService.register(email, phoneNumber, firstName, lastName, password);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-active-sale-staff-and-order-counts-list")
    public List<Map<String, Object>> getActiveSaleStaffWithOrderCounts() {
        return accountService.getActiveSaleStaffWithOrderCounts();
    }

    @GetMapping("/get-active-delivery-staff-and-order-counts-list")
    public List<Map<String, Object>> getActiveDeliveryStaffWithOrderCounts() {
        return accountService.getActiveDeliveryStaffWithOrderCounts();
    }


    @GetMapping("/view-accounts-list")
    public ResponseEntity<Response> viewAccountList(
            @RequestParam(value = "role", required = false) Role role,
            @RequestParam(value = "status", required = false) String status) {

        List<AccountResponse> accountResponses = accountService.getAccountList(role, status);

        Response response = Response.builder()
                .result(accountResponses)
                .isSuccess(true)
                .message("Fetched account list successfully")
                .statusCode(200)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/view-account-details/{accountId}")
    public ResponseEntity<Response> viewAccountDetails(@PathVariable Integer accountId) {
        AccountResponse accountResponse = accountService.getAccountDetails(accountId);

        if (accountResponse == null) {
            Response response = Response.builder()
                    .isSuccess(false)
                    .message("Account not found")
                    .statusCode(404)
                    .build();
            return ResponseEntity.status(404).body(response);
        }

        Response response = Response.builder()
                .result(accountResponse)
                .isSuccess(true)
                .message("Fetched account details successfully")
                .statusCode(200)
                .build();

        return ResponseEntity.ok(response);
    }
}
