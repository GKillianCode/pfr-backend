package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"bookingDate", "classroom", "slot", "event", "user"})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="booking_date")
    private LocalDate bookingDate;

    @ManyToOne
    @JoinColumn(name ="classroom_id", insertable = false, updatable = false)
    private Classroom classroom;

    @Column(name = "classroom_id")
    private Integer classroomId;

    @ManyToOne
    @JoinColumn(name="slot_id", insertable = false, updatable = false)
    private Slot slot;

    @Column(name = "slot_id")
    private Integer slotId;

    @ManyToOne
    @JoinColumn(name="event_id", insertable = false, updatable = false)
    private Event event;

    @Column(name = "event_id")
    private Integer eventId;

    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private Integer userId;

    public Booking(LocalDate bookingDate, Classroom classroom, Slot slot, Event event, User user) {
        this.bookingDate = bookingDate;
        this.classroom = classroom;
        this.slot = slot;
        this.event = event;
        this.user = user;
    }
}
