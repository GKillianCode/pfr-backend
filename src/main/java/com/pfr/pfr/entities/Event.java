package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {
        "name",
        "speakerFirstname",
        "speakerLastName",
        "speakerEmail",
        "speakerPhoneNumber",
        "description",
        "eventType",
        "promo"
})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name="speaker_firstname")
    private String speakerFirstname;

    @Column(name="speaker_lastname")
    private String speakerLastName;

    @Column(name="speaker_email")
    private String speakerEmail;

    @Column(name="speaker_phone_number")
    private String speakerPhoneNumber;

    @Column(name="description")
    private String description;

    @Column(name="participants_number")
    private Integer participantsNumber;

    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "promo_id")
    private Promo promo;

    @JoinColumn(name = "is_archived")
    private Boolean isArchived = false;

    public Event(String name, String speakerFirstname, String speakerLastName, String speakerEmail, String speakerPhoneNumber, String description, Integer participantsNumber, EventType eventType, Promo promo) {
        this.name = name;
        this.speakerFirstname = speakerFirstname;
        this.speakerLastName = speakerLastName;
        this.speakerEmail = speakerEmail;
        this.speakerPhoneNumber = speakerPhoneNumber;
        this.description = description;
        this.participantsNumber = participantsNumber;
        this.eventType = eventType;
        this.promo = promo;
    }
}
