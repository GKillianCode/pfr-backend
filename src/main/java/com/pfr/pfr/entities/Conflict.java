package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "conflict")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"comment", "createdAt", "booking", "user", "event"})
public class Conflict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="comment")
    private String comment;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name ="booking_id", insertable=false, updatable=false)
    private Booking booking;

    @Column(name="booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name ="user_id", insertable=false, updatable=false)
    private User user;

    @Column(name="user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name ="event_id", insertable=false, updatable=false)
    private Event event;

    @Column(name="event_id")
    private Integer eventId;

    public Conflict(String comment, Booking booking, User user, Event event) {
        this.comment = comment;
        this.booking = booking;
        this.user = user;
        this.event = event;
    }

    public Conflict(String comment, LocalDateTime createdAt, Booking booking, User user, Event event) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.booking = booking;
        this.user = user;
        this.event = event;
    }

    public Conflict(String comment, Integer bookingId, Integer userId, Integer eventId) {
        this.comment = comment;
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
    }

    public Conflict(String comment, LocalDateTime createdAt, Integer bookingId, Integer userId, Integer eventId) {
        this.comment = comment;
        this.bookingId = bookingId;
        this.createdAt = createdAt;
        this.userId = userId;
        this.eventId = eventId;
    }
}
