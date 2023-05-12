package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByClassroomId (Integer classroomId);

    @Query("SELECT b FROM Booking b WHERE b.event.promo.id = :promoId")
    List<Booking> findByPromoId(@Param("promoId") Integer promoId);


    public List<Booking> findAllByUser_Id(Integer id);

    public List<Booking> findAllByEventId(Integer id);


}
