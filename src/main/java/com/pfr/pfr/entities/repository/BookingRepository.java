package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
