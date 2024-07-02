package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiamondRepo extends JpaRepository<Diamond, Integer> {
    Optional<Diamond> findByCertificateNumber(String certificateNumber);

    Optional<Diamond> findById(Integer id);

    Optional<Diamond> findByName(String name);

    List<Diamond> findAll();

    @Transactional
    @Modifying
    @Query("UPDATE Diamond d SET d.statusDiamond = false WHERE d.id = :id")
    void softDeleteById(Integer id);

        @Query("SELECT d FROM Diamond d WHERE d.origin = :origin AND d.clarity = :clarity AND d.caratWeight = :caratWeight AND d.price = :price AND d.color = :color AND d.cut = :cut AND d.certificateNumber = :certificateNumber AND d.quantity = :quantity")
    Optional<Diamond> findExactMatch(
            //@Param("name") String name,
            @Param("origin") String origin,
            @Param("clarity") String clarity,
            @Param("caratWeight") float caratWeight,
            @Param("price") double price,
            @Param("color") String color,
            @Param("cut") String cut,
            @Param("certificateNumber") String certificateNumber,
            @Param("quantity") int quantity
    );

    @Query("SELECT d FROM Diamond d WHERE d.origin = :origin AND d.clarity = :clarity AND d.caratWeight = :caratWeight AND d.price = :price AND d.color = :color AND d.cut = :cut")
    Optional<Diamond> findPartialMatch(
            //@Param("name") String name,
            @Param("origin") String origin,
            @Param("clarity") String clarity,
            @Param("caratWeight") float caratWeight,
            @Param("price") double price,
            @Param("color") String color,
            @Param("cut") String cut
    );
}
