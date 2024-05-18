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
@Table(name = "`Account`")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    private Integer id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String pass;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "IsActive")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Roles roles;

    @OneToMany(mappedBy = "account")
    private List<Order> orderList;

    @OneToMany(mappedBy = "account")
    private List<Diamond> diamondList;

    @OneToMany(mappedBy = "account")
    private List<DiamondShell> diamondShellList;

    @OneToMany(mappedBy = "account")
    private List<Promotion> promotionList;
}
