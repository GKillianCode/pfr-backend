package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Conflict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConflictRepository extends JpaRepository<Conflict, Integer> {

    public List<Conflict> findByBookingId(Integer bookingId);

}
