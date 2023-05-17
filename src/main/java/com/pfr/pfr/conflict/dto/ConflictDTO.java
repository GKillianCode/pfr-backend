package com.pfr.pfr.conflict.dto;

import com.pfr.pfr.entities.Conflict;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ConflictDTO {

    private String comment;

    private LocalDateTime createdAt;

    private Integer bookingId;

    private Integer userId;

    private Integer eventId;

    public ConflictDTO(String comment, Integer bookingId, Integer userId, Integer eventId) {
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
    }

    public ConflictDTO(String comment, LocalDateTime createdAt, Integer bookingId, Integer userId, Integer eventId) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
    }

    public Conflict toEntity() {
        Conflict conflict = new Conflict();
        conflict.setComment(this.comment);
        conflict.setCreatedAt(this.createdAt);
        conflict.setBookingId(this.bookingId);
        conflict.setUserId(this.userId);
        conflict.setEventId(this.eventId);

        return conflict;
    }
}
