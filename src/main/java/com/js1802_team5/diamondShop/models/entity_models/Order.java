package com.js1802_team5.diamondShop.models.entity_models;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "`order`") // lí do có `...` là do Order bị trùng tên
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double totalPrice;

    private String address;

    @Column(name = "number_phone")
    private String phone;

    @Column(name = "customer_name")
    private String cusName;

    private Date warrantyStartDate;

    private Date warrantyEndDate;

    private boolean isCancel;

    private String description;

    @Column(name = "is_customer_delivered")
    private boolean isCustomerDelivered;

    @Column(name = "is_delivery_delivered")
    private boolean isDeliveryDelivered;

//    private String customerStatus;
//    private String deliveryStatus;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<AccountOrder> accountOrderList;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusOrder statusOrder;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //, fetch = FetchType.LAZY
    private List<OrderDetail> orderDetailList;

    @OneToMany(mappedBy = "order")
    private List<DateStatusOrder> dateStatusOrderList;
}
