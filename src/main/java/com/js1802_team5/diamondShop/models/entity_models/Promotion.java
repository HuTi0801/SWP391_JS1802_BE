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
@Table(name = "`promotion`")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "promotion_name")
    private String promotionName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "member_level_promotion")
    private String memberLevelPromotion;

    @Column(name = "discount_percent")
    private float discountPercent;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionDiamondShell> promotionDiamondShellList;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionDiamond> promotionDiamondList;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
