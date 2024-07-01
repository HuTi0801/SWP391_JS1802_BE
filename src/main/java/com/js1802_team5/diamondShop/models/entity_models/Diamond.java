package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.js1802_team5.diamondShop.models.request_models.Product;
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
@Table(name = "`diamond`")

public class Diamond implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "origin")
    private String origin;

    private String clarity;

    private float caratWeight;

    private double price;

    private String color;

    private String cut;

    private String certificateNumber;

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

    // Method to generate name
    public void generateName() {
        this.name = String.format("Diamond %s %s %s %s %s", caratWeight, clarity, color, cut, origin);
    }
}
