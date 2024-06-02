package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "`Size`")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SizeId")
    private Integer id;

    @NotNull(message = "Size Weight is mandatory")
    @Range(min = 1, max = 32, message = "Size Weight should be a positive number")
    @Column(name = "Size")
    private int size;

//    @OneToMany(mappedBy = "size")
//    private List<SizeDiamondShell> sizeDiamondShellList;
}
