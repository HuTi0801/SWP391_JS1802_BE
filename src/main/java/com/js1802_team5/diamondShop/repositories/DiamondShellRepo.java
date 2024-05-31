package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DiamondShellRepo extends JpaRepository<DiamondShell, Integer> {
    Optional<DiamondShell> findById(Integer id);
    @Transactional
    @Modifying
    @Query("UPDATE DiamondShell d SET d.statusDiamondShell = false WHERE d.id = :id")
    void softDeleteById(Integer id);
}
