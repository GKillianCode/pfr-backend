package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByPromoId(int promoId);

    List<Event> findEventByNameEqualsIgnoreCase(String name);

    // Get all active events
    List<Event> findEventByIsArchivedFalse();

    // Get all archived events
    List<Event> findEventByIsArchivedTrue();

}
