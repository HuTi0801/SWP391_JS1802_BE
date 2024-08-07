package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
    List<Account> findByRoleAndIsActiveOrderByUsernameAsc(Role role, boolean isActive);
    List<Account> findByRole(Role role);
    List<Account> findByIsActive(boolean isActive);
    List<Account> findByRoleAndIsActive(Role role, boolean isActive);

    @Query("SELECT a FROM Account a WHERE a.role IN :roles")
    List<Account> findAllByRoles(List<Role> roles);

    @Query("SELECT a FROM Account a WHERE a.role = com.js1802_team5.diamondShop.enums.Role.SALE_STAFF OR a.role = com.js1802_team5.diamondShop.enums.Role.DELIVERY_STAFF")
    List<Account> findAllByRoles();

    List<Account> findByIdIn(List<Integer> ids);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.isActive = false WHERE a.id = :id")
    void banAccountById(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.isActive = true WHERE a.id = :id")
    void unbanAccountById(Integer id);
}
