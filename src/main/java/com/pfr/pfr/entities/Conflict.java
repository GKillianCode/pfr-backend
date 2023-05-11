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

    @ManyToOne
    @JoinColumn(name ="booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="event_id")
    private Event event;

    public Conflict(String comment, LocalDateTime createdAt, Booking booking, User user, Event event) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.booking = booking;
        this.user = user;
        this.event = event;
    }
}
