package com.js1802_team5.diamondShop.models.entity_models;

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
@Entity
@Table(name = "`Status`")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusId")
    private Integer id;

    @Column(name = "StatusName")
    private String statusName;

    @OneToMany(mappedBy = "status")
    private List<Order> orderList;
}
