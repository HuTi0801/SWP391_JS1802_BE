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
@Table(name = "`DiamondShell`")
public class DiamondShell implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiamondShellId")
    private Integer id;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "SecondaryStoneType")
    private String secondaryStoneType;

    @Column(name = "Material")
    private String material;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Price")
    private double price;

    @Column(name = "Image")
    private String imageDiamondShell;

    @Column(name = "Status")
    private boolean statusDiamondShell;

    @OneToMany(mappedBy = "diamondShell")
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account account;

    @OneToMany(mappedBy = "diamondShell")
    private List<PromotionDiamondShell> promotionDiamondShellList;

    @OneToMany(mappedBy = "diamondShell")
    private List<SizeDiamondShell> sizeDiamondShellList;
}
