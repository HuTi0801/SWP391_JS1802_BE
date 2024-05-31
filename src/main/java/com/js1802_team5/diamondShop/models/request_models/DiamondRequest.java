package com.js1802_team5.diamondShop.models.request_models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Origin is mandatory and should not contain numbers or special characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Origin should only contain letters and spaces")
    private String origin;

    @NotBlank(message = "Clarity is mandatory")
    private String clarity;

    @NotNull(message = "Carat Weight is mandatory")
    @Range(min = 3, message = "Carat Weight should be a positive number")
    private float caratWeight;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be a positive number")
    private double price;

    @NotNull(message = "Color is mandatory")
    private String color;

    @NotBlank(message = "Cut is mandatory")
    private String cut;

    @NotBlank(message = "Certificate Number is mandatory")
    private String certificateNumber;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0, message = "Quantity should be at least 0")
    private int quantity;

    private String imageDiamond;

    @Pattern(regexp = "^(true|false)$", message = "Status must be either true or false")
    private boolean statusDiamond;
}
