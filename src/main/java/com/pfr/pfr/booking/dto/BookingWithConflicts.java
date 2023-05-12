package com.pfr.pfr.booking.dto;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Conflict;
import com.pfr.pfr.entities.Promo;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"booking", "conflicts"})
public class BookingWithConflicts {

    private Booking booking;

    private List<Conflict> conflicts;
}
