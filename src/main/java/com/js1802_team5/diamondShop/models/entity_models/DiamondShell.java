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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DiamondShell implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiamondShellId")
    private Integer id;

//    @NotNull(message = "Quantity is mandatory")
//    @Min(value = 0, message = "Quantity should be at least 0")
    @Column(name = "Quantity")
    private int quantity;

//    @NotBlank(message = "Secondary Stone Type is mandatory")
    @Column(name = "SecondaryStoneType")
    private String secondaryStoneType;

    @Column(name = "Material")
    private String material;

//    @Pattern(regexp = "^(male|female)$", message = "Gender must be either male or female")
    @Column(name = "Gender")
    private String gender;

//    @NotNull(message = "Price is mandatory")
//    @Positive(message = "Price should be a positive number")
    @Column(name = "Price")
    private double price;

    @Column(name = "Image")
    private String imageDiamondShell;

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
