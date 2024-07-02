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
    private String name;
    private int quantity;
    private String secondaryStoneType;
    private String material;
    private String gender;
    private double price;

    @Column(name = "image")
    private String imageDiamondShell;

    @Column(name = "status")
    private boolean statusDiamondShell;

    @OneToMany(mappedBy = "diamondShell")
    @JsonManagedReference
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @OneToMany(mappedBy = "diamondShell")
    @JsonManagedReference
    private List<PromotionDiamondShell> promotionDiamondShellList;

    @OneToMany(mappedBy = "diamondShell")
    @JsonManagedReference
    private List<SizeDiamondShell> sizeDiamondShellList;

    // Method to generate name
    public void generateName() {
        this.name = String.format("Diamond Shell %s %s %s", gender, material, secondaryStoneType);
    }
}
