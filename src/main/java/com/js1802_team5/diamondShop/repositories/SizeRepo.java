package com.js1802_team5.diamondShop.repositories;

import com.js1802_team5.diamondShop.models.entity_models.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepo extends JpaRepository<Size, Integer> {
    Optional<Size> findBySize(int id);
}
