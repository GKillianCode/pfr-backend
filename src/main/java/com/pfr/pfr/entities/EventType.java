package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_type")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "isExceptionalClosure"})
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;
    
    @Column(name = "is_exceptional_closure")
    private Boolean isExceptionalClosure;

    public EventType(String name, Boolean isExceptionalClosure) {
        this.name = name;
        this.isExceptionalClosure = isExceptionalClosure;
    }
}
