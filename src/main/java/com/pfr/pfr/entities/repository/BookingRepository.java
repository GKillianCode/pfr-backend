package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import com.pfr.pfr.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.event.promo.id = :promoId")
    List<Booking> findByPromoId(@Param("promoId") Integer promoId);


    public List<Booking> findAllByUser_Id(Integer id);

    @Query("SELECT b FROM Booking b WHERE b.bookingDate = :date AND b.slotId = :slotId AND b.classroomId = :classroomId ")
    public List<Booking> findByDateAndSlotAndClassroom(
            @Param("date") LocalDate date,
            @Param("slotId") Integer slotId,
            @Param("classroomId") Integer classroomId
    );


}
