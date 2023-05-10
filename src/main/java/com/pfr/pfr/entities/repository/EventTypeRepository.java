package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}
