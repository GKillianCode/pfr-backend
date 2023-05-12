package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByClassroomId (Integer classroomId);
}
