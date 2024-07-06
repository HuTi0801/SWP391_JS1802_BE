package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import com.js1802_team5.diamondShop.models.entity_models.Customer;
import com.js1802_team5.diamondShop.models.request_models.CustomerRequest;
import com.js1802_team5.diamondShop.models.response_models.CustomerResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
import com.js1802_team5.diamondShop.repositories.CustomerRepo;
import com.js1802_team5.diamondShop.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final AccountRepo accountRepo;
    @Override
    @Transactional
    public Response updateCustomer(int customerId, CustomerRequest customerRequest) {
        Response response = new Response();
        try {
            Optional<Customer> customerOpt = customerRepo.findById(customerId);
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                Account account = customer.getAccount();

                if (account.getRole() != Role.CUSTOMER) {
                    response.setStatusCode(403);
                    response.setSuccess(false);
                    response.setMessage("Only customers can update their information.");
                    response.setResult(null);
                    return response;
                }

                // Update account fields
                account.setFirstName(customerRequest.getFirstName());
                account.setLastName(customerRequest.getLastName());
                accountRepo.save(account);

                // Update customer fields
                customer.setPhone(customerRequest.getPhone());
                customer.setEmail(customerRequest.getEmail());
                customerRepo.save(customer);

                // Prepare and return response
                CustomerResponse customerResponse = new CustomerResponse();
                customerResponse.setCustomerID(customer.getId());
                customerResponse.setFirstName(account.getFirstName());
                customerResponse.setLastName(account.getLastName());
                customerResponse.setPhone(customer.getPhone());
                customerResponse.setEmail(customer.getEmail());
                customerResponse.setPoint(customer.getPoint());
                customerResponse.setMemberLevel(customer.getMemberLevel());

                response.setStatusCode(200);
                response.setSuccess(true);
                response.setMessage("Customer updated successfully.");
                response.setResult(customerResponse);
            } else {
                response.setStatusCode(404);
                response.setSuccess(false);
                response.setMessage("Customer not found with id: " + customerId);
                response.setResult(null);
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
