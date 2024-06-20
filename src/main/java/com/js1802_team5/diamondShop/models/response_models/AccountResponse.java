package com.js1802_team5.diamondShop.models.response_models;
import com.js1802_team5.diamondShop.enums.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Integer id;
    private String username;
    private String pass;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Role role;
    // Manually define setter for customerResponse
    @Setter
    private CustomerResponse customerResponse;

}
