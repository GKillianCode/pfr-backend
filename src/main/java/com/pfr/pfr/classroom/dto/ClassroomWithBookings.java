package com.pfr.pfr.classroom.dto;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"classroom", "bookings"})
public class ClassroomWithBookings {

    private Classroom classroom;

    private List<Booking> bookings;
}
