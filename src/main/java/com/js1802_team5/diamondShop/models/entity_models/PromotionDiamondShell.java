package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "`promotion_diamond_shell`")
public class PromotionDiamondShell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "diamond_shell_id")
    @JsonBackReference
    private DiamondShell diamondShell;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
