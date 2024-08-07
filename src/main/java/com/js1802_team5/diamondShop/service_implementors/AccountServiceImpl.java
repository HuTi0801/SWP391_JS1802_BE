package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.MemberLevel;
import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import com.js1802_team5.diamondShop.models.entity_models.Customer;
import com.js1802_team5.diamondShop.models.response_models.AccountResponse;
import com.js1802_team5.diamondShop.models.response_models.CustomerResponse;
import com.js1802_team5.diamondShop.models.response_models.LoginResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
import com.js1802_team5.diamondShop.repositories.CustomerRepo;
import com.js1802_team5.diamondShop.services.AccountService;
import com.js1802_team5.diamondShop.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AccountRepo accountRepo;
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Response login(String username, String password) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authToken);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            // Retrieve user role from database
            Account account = accountRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Integer customerId = null;
            if (account.getRole() == Role.CUSTOMER) {
                Customer customer = customerRepo.findByAccountId(account.getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
                customerId = customer.getId();
            }
            return Response.builder()
                    .result(new LoginResponse(token, account.getUsername(), account.getAuthorities().toString(), account.getId(), customerId))
                    .isSuccess(true)
                    .message("Login successful")
                    .statusCode(200)
                    .build();
        } catch (AuthenticationException e) {
            return Response.builder()
                    .result(null)
                    .isSuccess(false)
                    .message("User not found")
                    .statusCode(401)
                    .build();
        }
    }

    @Override
    public Response logout(String token) {
        try {
            if (jwtService.checkTokenIsValid(token)) {
                jwtService.invalidateToken(token);
                return Response.builder()
                        .result(null)
                        .isSuccess(true)
                        .message("Logout successful")
                        .statusCode(200)
                        .build();
            } else {
                return Response.builder()
                        .result(null)
                        .isSuccess(false)
                        .message("Invalid token")
                        .statusCode(400)
                        .build();
            }
        } catch (Exception e) {
            return Response.builder()
                    .result(null)
                    .isSuccess(false)
                    .message("Logout failed")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public Response forgetPassword(String phone, String newPassword) {
        Account account = accountRepo.findByUsername(phone).orElse(null);
        if (account == null) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Phone number does not exist.")
                    .build();
        }

        if (!isValidPassword(newPassword)) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Invalid: Password must be no more than 8 characters and only include integers")
                    .build();
        }

        account.setPass(passwordEncoder.encode(newPassword));
        accountRepo.save(account);

        return Response.builder()
                .isSuccess(true)
                .message("Password updated successfully")
                .build();
    }

    public Response register(String email, String phoneNumber, String firstName, String lastName, String password) {
        if (customerRepo.findByEmail(email).isPresent()) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Email already exists.")
                    .build();
        }

        if (customerRepo.findByPhone(phoneNumber).isPresent()) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Phone number already exists.")
                    .build();
        }

        if (!isValidEmail(email)) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Email invalid.")
                    .build();
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Phone invalid: phone has 10 numbers")
                    .build();
        }

        if (!isValidName(firstName) || !isValidName(lastName)) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Name invalid: No more than 10 characters, all must be letters")
                    .build();
        }

        if (!isValidPassword(password)) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Password invalid: Password must be no more than 8 characters and only include integers")
                    .build();
        }

        Account account = new Account();
        account.setUsername(phoneNumber);
        account.setPass(passwordEncoder.encode(password));
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setActive(true);
        account.setRole(Role.CUSTOMER);
        accountRepo.save(account);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPhone(phoneNumber);
        customer.setPoint(0);
        customer.setMemberLevel(MemberLevel.SILVER.toString());
        customer.setAccount(account);
        customerRepo.save(customer);

        return Response.builder()
                .isSuccess(true)
                .message("Register Successfully!")
                .build();
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@gmail.com");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]{1,10}");
    }

    private boolean isValidPassword(String password) {
        return password.matches("\\d+") && password.length() <= 8;
    }

    public List<Map<String, Object>> getActiveSaleStaffWithOrderCounts() {
        List<Map<String, Object>> result = new ArrayList<>();

        // Lấy danh sách SALE_STAFF có isActive = true
        List<Account> saleStaff = accountRepo.findByRoleAndIsActiveOrderByUsernameAsc(Role.SALE_STAFF, true);

        // Tính số lượng order cho từng nhân viên SALE_STAFF và thêm vào kết quả
        for (Account staff : saleStaff) {
            Map<String, Object> staffInfo = new HashMap<>();
            staffInfo.put("accountId", staff.getId());
            staffInfo.put("username", staff.getUsername());
            staffInfo.put("orderCount", staff.getAccountOrderList().size()); // Số lượng order của SALE_STAFF
            result.add(staffInfo);
        }

        // Sắp xếp danh sách kết quả theo số lượng order từ thấp đến cao
        result.sort((staff1, staff2) -> {
            int orderCount1 = (int) staff1.get("orderCount");
            int orderCount2 = (int) staff2.get("orderCount");
            return Integer.compare(orderCount1, orderCount2);
        });

        return result;
    }

    public List<Map<String, Object>> getActiveDeliveryStaffWithOrderCounts() {
        List<Map<String, Object>> result = new ArrayList<>();

        List<Account> deliveryStaff = accountRepo.findByRoleAndIsActiveOrderByUsernameAsc(Role.DELIVERY_STAFF, true);

        for (Account staff : deliveryStaff) {
            Map<String, Object> staffInfo = new HashMap<>();
            staffInfo.put("accountId", staff.getId());
            staffInfo.put("username", staff.getUsername());
            staffInfo.put("orderCount", staff.getAccountOrderList().size());
            result.add(staffInfo);
        }

        result.sort((staff1, staff2) -> {
            int orderCount1 = (int) staff1.get("orderCount");
            int orderCount2 = (int) staff2.get("orderCount");
            return Integer.compare(orderCount1, orderCount2);
        });

        return result;
    }

    @Override
    public List<AccountResponse> getAccountList(Role role, String status) {
        List<Account> accounts;

        if (role != null && status != null) {
            boolean isActive = "active".equalsIgnoreCase(status);
            accounts = accountRepo.findByRoleAndIsActive(role, isActive);
        } else if (role != null) {
            accounts = accountRepo.findByRole(role);
        } else if (status != null) {
            boolean isActive = "active".equalsIgnoreCase(status);
            accounts = accountRepo.findByIsActive(isActive);
        } else {
            accounts = accountRepo.findAll();
        }

        List<AccountResponse> accountResponses = new ArrayList<>();

        for (Account account : accounts) {
            AccountResponse accountResponse = AccountResponse.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .pass(account.getPass())
                    .firstName(account.getFirstName())
                    .lastName(account.getLastName())
                    .isActive(account.isActive())
                    .role(account.getRole())
                    .build();

            if (Role.CUSTOMER.equals(account.getRole())) {
                CustomerResponse customerResponse = getCustomerByAccountId(account.getId());
                if (customerResponse != null) {
                    accountResponse.setCustomerResponse(customerResponse);
                }
            }

            accountResponses.add(accountResponse);
        }

        return accountResponses;
    }

    @Override
    public AccountResponse getAccountDetails(Integer accountId) {
        Account account = accountRepo.findById(accountId).orElse(null);
        if (account == null) {
            return null;
        }

        AccountResponse accountResponse = AccountResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .pass(account.getPass())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .isActive(account.isActive())
                .role(account.getRole())
                .build();

        if (Role.CUSTOMER.equals(account.getRole())) {
            CustomerResponse customerResponse = getCustomerByAccountId(accountId);
            if (customerResponse != null) {
                accountResponse.setCustomerResponse(customerResponse);
            }
        }

        return accountResponse;
    }

    @Override
    public CustomerResponse getCustomerByAccountId(Integer accountId) {
        Optional<Customer> optionalCustomer = customerRepo.findByAccountId(accountId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return CustomerResponse.builder()
                    .customerID(customer.getId())
                    .email(customer.getEmail())
                    .phone(customer.getPhone())
                    .point(customer.getPoint())
                    .memberLevel(customer.getMemberLevel())
                    .build();
        }
        return null;
    }

    @Override
    public Response banAccount(Integer id) {
        var response = new Response();
        try {
            var accountOptional = accountRepo.findById(id);
            if (accountOptional.isPresent()) {
                var account = accountOptional.get();
                if (account.getRole() == Role.ADMIN) {  // Kiểm tra vai trò của tài khoản
                    response.setMessage("Cannot ban an ADMIN account!");
                    response.setResult(id);
                    response.setSuccess(false);
                    response.setStatusCode(403);  // HTTP Status Code for Forbidden
                } else {
                    accountRepo.banAccountById(id);
                    response.setMessage("Account banned successfully!");
                    response.setResult(null);
                    response.setSuccess(true);
                    response.setStatusCode(200);
                }
            } else {
                response.setMessage("Account not found!");
                response.setResult(id);
                response.setSuccess(false);
                response.setStatusCode(404);
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
    public Response unbanAccount(Integer id) {
        var response = new Response();
        try {
            var account = accountRepo.findById(id);
            if (account.isPresent()) {
                accountRepo.unbanAccountById(id);
                response.setMessage("Account unbanned successfully!");
                response.setResult(null);
                response.setSuccess(true);
                response.setStatusCode(200);
            } else {
                response.setMessage("Account not found!");
                response.setResult(id);
                response.setSuccess(false);
                response.setStatusCode(404);
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
