package com.js1802_team5.diamondShop.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`Order`") // lí do có `...` là do Order bị trùng tên
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Integer id;

    @Column(name = "TotalPrice")
    private double totalPrice;

    @Column(name = "Address")
    private String address;

    @Column(name = "PurchaseDate")
    private Date purchaseDate;

    @Column(name = "WarrantyStartDate")
    private Date warrantyStartDate;

    @Column(name = "WarrantyEndDate")
    private Date warrantyEndDate;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "StatusId")
    private Status status;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetailList;
}