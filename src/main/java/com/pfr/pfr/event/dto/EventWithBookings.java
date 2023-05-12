package com.pfr.pfr.event.dto;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Event;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"event", "bookings"})
public class EventWithBookings {
    private Event event;
    private List<Booking> bookings;
}
