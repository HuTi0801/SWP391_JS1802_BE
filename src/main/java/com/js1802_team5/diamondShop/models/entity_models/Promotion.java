package com.js1802_team5.diamondShop.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`Promotion`")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PromotionId")
    private Integer id;

    @Column(name = "PromotionName")
    private String promotionName;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "Description")
    private String description;

    @Column(name = "Type")
    private String type;

    @Column(name = "MemberLevelPromotion")
    private String memberLevelPromotion;

    @Column(name = "DiscountPercent")
    private float discountPercent;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionDiamondShell> promotionDiamondShellList;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionDiamond> promotionDiamondList;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account account;
}
