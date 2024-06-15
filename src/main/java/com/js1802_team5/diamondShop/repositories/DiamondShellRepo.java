package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
}
