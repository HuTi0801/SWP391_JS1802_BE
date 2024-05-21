package com.js1802_team5.diamondShop.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`PromotionDiamond`")
public class PromotionDiamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PromotionDiamondsId")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "DiamondId")
    private Diamond diamond;

    @ManyToOne
    @JoinColumn(name = "PromotionId")
    private Promotion promotion;
}
