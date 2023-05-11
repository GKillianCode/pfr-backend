package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    public List<Booking> findAllByUser_Id(Integer id);


}
