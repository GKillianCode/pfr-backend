package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "slot")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"weekDay", "daytime", "isBookable"})
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "week_day")
    private String weekDay;

    @Column(name = "daytime")
    private String daytime;

    @Column(name = "is_bookable")
    private Boolean isBookable;

    public Slot(String weekDay, String daytime, Boolean isBookable) {
        this.weekDay = weekDay;
        this.daytime = daytime;
        this.isBookable = isBookable;
    }
}
