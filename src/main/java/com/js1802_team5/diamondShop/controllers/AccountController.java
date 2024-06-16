package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}