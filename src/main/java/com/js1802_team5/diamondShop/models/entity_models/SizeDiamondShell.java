package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "`SizeDiamondShell`")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SizeDiamondShell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SizeDiamondShellId")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "DiamondShellId")
    private DiamondShell diamondShell;

//    @ManyToOne
//    @JoinColumn(name = "SizeId")
//    private Size size;
}
