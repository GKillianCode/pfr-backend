package com.pfr.pfr.booking.dto;

import com.pfr.pfr.entities.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"bookingDate", "classroomId", "slotId", "eventId"})
public class BookingDTO {

    private LocalDate bookingDate;

    private Integer classroomId;

    private Integer slotId;

    private Integer eventId;

    private Integer userId;

    public BookingDTO(LocalDate bookingDate, Integer classroomId, Integer slotId, Integer eventId, Integer userId) {
        this.bookingDate = bookingDate;
        this.classroomId = classroomId;
        this.slotId = slotId;
        this.eventId = eventId;
        this.userId = userId;
    }

    public Booking toEntity() {
        Booking booking = new Booking();
        booking.setBookingDate(this.bookingDate);
        booking.setClassroomId(this.classroomId);
        booking.setSlotId(this.slotId);
        booking.setEventId(this.eventId);
        booking.setUserId(this.userId);

        return booking;
    }

}


