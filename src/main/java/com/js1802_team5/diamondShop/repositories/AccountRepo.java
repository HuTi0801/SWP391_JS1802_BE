package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
    List<Account> findByRoleAndIsActiveOrderByUsernameAsc(Role role, boolean isActive);
    List<Account> findByRole(Role role);
    List<Account> findByIsActive(boolean isActive);
    List<Account> findByRoleAndIsActive(Role role, boolean isActive);
}
