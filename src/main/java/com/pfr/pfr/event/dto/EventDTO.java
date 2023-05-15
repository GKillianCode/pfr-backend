package com.pfr.pfr.event.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "speakerFirstname", "speakerLastName", "speakerEmail", "speakerPhoneNumber", "description", "eventTypeId", "promoId"})
public class EventDTO {

    private String name;

    private String speakerFirstname;

    private String speakerLastName;

    private String speakerEmail;

    private String speakerPhoneNumber;

    private String description;

    private Integer participantsNumber;

    private Integer eventTypeId;

    private Integer promoId;

    private Boolean isArchived;

    public EventDTO(String name, String speakerFirstname, String speakerLastName, String speakerEmail, String speakerPhoneNumber, String description, Integer participantsNumber, Integer eventTypeId, Integer promoId) {
        this.name = name;
        this.speakerFirstname = speakerFirstname;
        this.speakerLastName = speakerLastName;
        this.speakerEmail = speakerEmail;
        this.speakerPhoneNumber = speakerPhoneNumber;
        this.description = description;
        this.participantsNumber = participantsNumber;
        this.eventTypeId = eventTypeId;
        this.promoId = promoId;
    }
}
