package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties("customer")
@Entity
@Table(name = "`Customer`")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId")
    private Integer id;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phone;

    @Column(name = "Point")
    private int point;

    @Column(name = "MemberLevel")
    private String memberLevel;

    @OneToOne
    @JoinColumn(name = "AccountId")
    private Account account;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Order> orderList;
}
