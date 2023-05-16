package com.pfr.pfr.event_type.dto;

import com.pfr.pfr.entities.EventType;
import jakarta.persistence.Column;
import jdk.jfr.BooleanFlag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "isExceptionalClosure", "isArchived"})
public class EventTypeDTO {

    private String name;

    private Boolean isExceptionalClosure;

    @BooleanFlag
    private Boolean isArchived;

    public EventTypeDTO(String name, Boolean isExceptionalClosure) {
        this.name = name;
        this.isExceptionalClosure = isExceptionalClosure;
    }

    public boolean equalsEventType(EventType eventType) {
        return Objects.equals(name, eventType.getName()) && Objects.equals(isExceptionalClosure, eventType.getIsExceptionalClosure());
    }

}
