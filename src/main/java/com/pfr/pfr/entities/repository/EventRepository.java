package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByPromoId(int promoId);

}
