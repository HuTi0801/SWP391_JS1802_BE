package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiamondShellRepo extends JpaRepository<DiamondShell, Integer> {
    @NonNull
    Optional<DiamondShell> findById(@NonNull Integer id);

    Optional<DiamondShell> findByName(String name);

    List<DiamondShell> findAll();

    @Transactional
    @Modifying
    @Query("UPDATE DiamondShell d SET d.statusDiamondShell = false WHERE d.id = :id")
    void softDeleteById(Integer id);

    @Query("SELECT d FROM DiamondShell d WHERE d.gender = :gender AND d.material = :material AND d.secondaryStoneType = :secondaryStoneType AND d.price = :price AND d.imageDiamondShell = :imageDiamondShell AND d.statusDiamondShell = :statusDiamondShell")
    Optional<DiamondShell> findExactMatch(
            @Param("gender") String gender,
            @Param("material") String material,
            @Param("secondaryStoneType") String secondaryStoneType,
            @Param("price") double price,
            @Param("imageDiamondShell") String imageDiamondShell,
            @Param("statusDiamondShell") boolean statusDiamondShell
    );
}
