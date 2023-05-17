package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByClassroomId (Integer classroomId);

    @Query("SELECT b FROM Booking b WHERE b.event.promo.id = :promoId")
    List<Booking> findByPromoId(@Param("promoId") Integer promoId);

    public Integer countByUserId(Integer userId);
    public List<Booking> findAllByUserIdOrderByBookingDateAscSlotAsc(Integer id, Pageable pageable);

    List<Booking> findAllByUserIdAndBookingDateBetweenOrderByBookingDateAscSlotAsc(Integer id, LocalDate startDate, LocalDate endDate);

    public List<Booking> findAllByEventId(Integer id);

    @Query("SELECT b FROM Booking b WHERE b.bookingDate = :date AND b.slotId = :slotId AND b.classroomId = :classroomId ")
    public List<Booking> findByDateAndSlotAndClassroom(
            @Param("date") LocalDate date,
            @Param("slotId") Integer slotId,
            @Param("classroomId") Integer classroomId
    );

    List<Booking> findByClassroomIdAndBookingDateBetweenOrderByBookingDateAscSlotAsc(Integer id, LocalDate startDate, LocalDate endDate);
}
