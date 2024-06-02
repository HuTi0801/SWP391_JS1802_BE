package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.SizeDiamondShell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeDiamondShellRepo extends JpaRepository<SizeDiamondShell, Integer> {
//ds.size.size is directly access the value of the size field in the Size entities.
    @Query("SELECT ds.size.size FROM SizeDiamondShell ds WHERE ds.diamondShell.id = :diamondShellId")
    List<Integer> findSizesByDiamondShellId(@Param("diamondShellId") int diamondShellId);
}
