package com.pfr.pfr.slot.dto;

import com.pfr.pfr.entities.Slot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.jfr.BooleanFlag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"weekDay", "daytime", "isBookable", "isArchived"})
public class SlotDTO {

    private String weekDay;

    private String daytime;

    private Boolean isBookable;

    @BooleanFlag
    private Boolean isArchived;

    public boolean equalsSlot(Slot slot) {
        return (weekDay.equals(slot.getWeekDay()) && daytime.equals(slot.getDaytime()));
    }

    public SlotDTO(String weekDay, String daytime, Boolean isBookable) {
        this.weekDay = weekDay;
        this.daytime = daytime;
        this.isBookable = isBookable;
    }
}
