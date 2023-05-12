package com.pfr.pfr.user.dto;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.user.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"user", "bookings", "totalBookingCount"})
public class UserWithBookings {

    private User user;

    private List<Booking> bookings;

    private Integer totalBookingCount;
}
