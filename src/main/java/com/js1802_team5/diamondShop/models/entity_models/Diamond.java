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
@Table(name = "`Diamond`")
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiamondId")
    private Integer id;

    @Column(name = "Origin")
    private String origin;

    @Column(name = "Clarity")
    private String clarity;

    @Column(name = "CaratWeight")
    private float caratWeight;

    @Column(name = "Price")
    private double price;

    @Column(name = "Color")
    private char color;

    @Column(name = "Cut")
    private String cut;

    @Column(name = "CertificateNumber")
    private String certificateNumber;

    @Column(name = "Quantity")
    private int quantity;

    @OneToMany(mappedBy = "diamond")
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account account;

    @OneToMany(mappedBy = "diamond")
    private List<PromotionDiamond> promotionDiamondList;
}
