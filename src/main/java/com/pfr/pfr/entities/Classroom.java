package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classroom")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "capacity", "location"})
public class Classroom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "location_id", updatable = true, insertable = true)
    private Location location;

    @Column(name = "is_bookable")
    private Boolean isBookable;

    @Column(name = "is_archived", columnDefinition = "TINYINT(1) default 0")
    private Boolean isArchived;


    public Classroom(String name, Integer capacity, Location location, Boolean isBookable) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.isBookable = isBookable;
        this.isArchived = false;
    }
}
