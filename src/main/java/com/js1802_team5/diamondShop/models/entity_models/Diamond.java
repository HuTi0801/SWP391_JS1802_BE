package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "`diamond`")

public class Diamond implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "origin")
    private String origin;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "carat_weight")
    private float caratWeight;

    @Column(name = "price")
    private double price;

    @Column(name = "color")
    private String color;

    @Column(name = "cut")
    private String cut;

    @Column(name = "certificate_number")
    private String certificateNumber;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "image")
    private String imageDiamond;

    @Column(name = "status")
    private boolean statusDiamond;

    @OneToMany(mappedBy = "diamond")
    @JsonManagedReference
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @OneToMany(mappedBy = "diamond")
    @JsonManagedReference
    private List<PromotionDiamond> promotionDiamondList;
}
