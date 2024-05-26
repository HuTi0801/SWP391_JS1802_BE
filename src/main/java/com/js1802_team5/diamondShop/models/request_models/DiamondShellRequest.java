package com.js1802_team5.diamondShop.models.request_models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DiamondShellRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiamondShellId")
    private Integer id;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0, message = "Quantity should be at least 0")
    @Column(name = "Quantity")
    private int quantity;

    @NotBlank(message = "Secondary Stone Type is mandatory")
    @Column(name = "SecondaryStoneType")
    private String secondaryStoneType;

    @NotBlank(message = "Material is mandatory and should not contain numbers or special characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Material should only contain letters and spaces")
    @Column(name = "Material")
    private String material;

    @Pattern(regexp = "^(male|female)$", message = "Gender must be either male or female")
    @Column(name = "Gender")
    private String gender;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be a positive number")
    @Column(name = "Price")
    private double price;

    @Column(name = "Image")
    private String imageDiamondShell;
}
