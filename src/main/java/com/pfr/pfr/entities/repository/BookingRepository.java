package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByClassroomId (Integer classroomId);

    @Query("SELECT b FROM Booking b WHERE b.event.promo.id = :promoId")
    List<Booking> findByPromoId(@Param("promoId") Integer promoId);

    public Integer countByUserId(Integer userId);
    public List<Booking> findAllByUserIdOrderByBookingDateAscSlotAsc(Integer id, Pageable pageable);

    public List<Booking> findAllByEventId(Integer id);



}
