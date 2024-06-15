package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
}
