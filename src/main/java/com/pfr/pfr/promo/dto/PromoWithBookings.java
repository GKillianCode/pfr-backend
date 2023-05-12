package com.pfr.pfr.promo.dto;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.Promo;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"promo", "bookings"})
public class PromoWithBookings {

    private Promo promo;

    private List<Booking> bookings;
}
