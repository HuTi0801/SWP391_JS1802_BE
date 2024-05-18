package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondRepo extends JpaRepository<Diamond, Integer> {
}
