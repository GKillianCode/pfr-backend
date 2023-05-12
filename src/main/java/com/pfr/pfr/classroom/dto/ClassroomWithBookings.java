package com.pfr.pfr.classroom.dto;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomWithBookings {

    private Classroom classroom;

    private List<Booking> bookings;
}
