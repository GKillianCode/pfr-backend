package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Conflict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConflictRepository extends JpaRepository<Conflict, Integer> {
}
