package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {

    List<EventType> findEventTypeByIsArchivedFalse();

    List<EventType> findEventTypeByIsArchivedTrue();

}
