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
@Table(name = "`PromotionDiamondShell`")
public class PromotionDiamondShell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PromotionDiamondShellId")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "DiamondShellId")
    private DiamondShell diamondShell;

    @ManyToOne
    @JoinColumn(name = "PromotionId")
    private Promotion promotion;
}
