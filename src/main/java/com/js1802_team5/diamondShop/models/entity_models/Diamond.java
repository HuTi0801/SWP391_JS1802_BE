package com.js1802_team5.diamondShop.models.entity_models;

import com.js1802_team5.diamondShop.models.request_models.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`Diamond`")
public class Diamond implements Product {
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
    private String color;

    @Column(name = "Cut")
    private String cut;

    @Column(name = "CertificateNumber")
    private String certificateNumber;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Image")
    private String imageDiamond;

    @Column(name = "Status")
    private boolean statusDiamond;

    @OneToMany(mappedBy = "diamond")
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account account;

    @OneToMany(mappedBy = "diamond")
    private List<PromotionDiamond> promotionDiamondList;
}
