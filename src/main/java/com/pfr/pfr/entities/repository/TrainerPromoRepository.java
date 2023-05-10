package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.TrainerPromo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerPromoRepository extends JpaRepository<TrainerPromo, Integer> {
    List<com.pfr.pfr.entities.TrainerPromo> findAll();

    List<TrainerPromo> findByUserId(int userId);
}