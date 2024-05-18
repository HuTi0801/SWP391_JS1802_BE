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
@Table(name = "`DiamondShell`")
public class DiamondShell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiamondShellId")
    private Integer id;

    @Column(name = "Size")
    private int size;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "SecondaryStoneType")
    private String secondaryStoneType;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Price")
    private double price;

    @OneToMany(mappedBy = "diamondShell")
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account account;

    @OneToOne
    @JoinColumn(name = "DiamondId")
    private Diamond diamond;

    @OneToMany(mappedBy = "diamondShell")
    private List<PromotionDiamondShell> promotionDiamondShellList;
}
