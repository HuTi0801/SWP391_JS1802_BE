package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.js1802_team5.diamondShop.models.request_models.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "`diamond_shell`")
public class DiamondShell implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "secondary_stone_type")
    private String secondaryStoneType;

    @Column(name = "material")
    private String material;

    @Column(name = "gender")
    private String gender;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String imageDiamondShell;

    @Column(name = "status")
    private boolean statusDiamondShell;

    @OneToMany(mappedBy = "diamondShell")
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "diamondShell")
    private List<PromotionDiamondShell> promotionDiamondShellList;

    @OneToMany(mappedBy = "diamondShell")
    private List<SizeDiamondShell> sizeDiamondShellList;
}
