package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

    Optional<Slot> findSlotById(Integer slotId);

    List<Slot> findSlotByIsArchivedFalse();

    List<Slot> findSlotByIsArchivedTrue();
}
