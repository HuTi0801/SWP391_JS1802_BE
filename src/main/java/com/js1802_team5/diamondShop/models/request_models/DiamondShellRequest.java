package com.js1802_team5.diamondShop.models.request_models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondShellRequest {
    private Integer id;

    private String name;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0, message = "Quantity should be at least 0")
    private int quantity;

    @NotBlank(message = "Secondary Stone Type is mandatory")
    private String secondaryStoneType;

    private String material;

    //@Pattern(regexp = "^(male|female)$", message = "Gender must be either male or female")
    private String gender;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be a positive number")
    private double price;

    private String imageDiamondShell;

    //@Pattern(regexp = "^(true|false)$", message = "Status must be either true or false")
    private boolean statusDiamondShell;

    private List<Integer> sizeIds;

    private double min_price;

    private double max_price;
}
