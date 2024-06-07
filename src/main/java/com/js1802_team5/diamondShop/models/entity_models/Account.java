package com.js1802_team5.diamondShop.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`account`")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String pass;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_active")
    private boolean isActive;

    private String roles;

    @OneToMany(mappedBy = "account")
    private List<AccountOrder> accountOrderList;

    @OneToMany(mappedBy = "account")
    private List<Diamond> diamondList;

    @OneToMany(mappedBy = "account")
    private List<DiamondShell> diamondShellList;

    @OneToMany(mappedBy = "account")
    private List<Promotion> promotionList;
}
