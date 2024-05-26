package com.js1802_team5.diamondShop.models.entity_models;

import com.js1802_team5.diamondShop.models.request_models.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "`Diamond`")
public class Diamond implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiamondId")
    private Integer id;

    @NotBlank(message = "Origin is mandatory and should not contain numbers or special characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Origin should only contain letters and spaces")
    @Column(name = "Origin")
    private String origin;

    @NotBlank(message = "Clarity is mandatory")
    @Column(name = "Clarity")
    private String clarity;

    @NotNull(message = "Carat Weight is mandatory")
    @Range(min = 3, message = "Carat Weight should be a positive number")
    @Column(name = "CaratWeight")
    private float caratWeight;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price should be a positive number")
    @Column(name = "Price")
    private double price;

    @NotNull(message = "Color is mandatory")
    @Column(name = "Color")
    private String color;

    @NotBlank(message = "Cut is mandatory")
    @Column(name = "Cut")
    private String cut;

    @NotBlank(message = "Certificate Number is mandatory")
    @Column(name = "CertificateNumber")
    private String certificateNumber;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0, message = "Quantity should be at least 0")
    @Column(name = "Quantity")
    private int quantity;

//    @Pattern(
//            regexp = "^https://firebasestorage.googleapis.com/v0/b/[a-zA-Z0-9_-]+.appspot.com/o/[a-zA-Z0-9_-]+.png?alt=media&token=[a-zA-Z0-9_-]+$",
//            message = "Image URL should be a valid Firebase Storage URL"
//    )
    @Column(name = "Image")
    private String imageDiamond;

    @OneToMany(mappedBy = "diamond")
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account account;

    @OneToMany(mappedBy = "diamond")
    private List<PromotionDiamond> promotionDiamondList;
}
