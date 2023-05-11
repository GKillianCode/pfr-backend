package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "promo")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "studentsNumber", "isActive"})
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name = "students_number")
    private Integer studentsNumber;

    @Column(name="is_active")
    private Boolean isActive;

//    @ManyToMany(mappedBy = "promos")
//    private List<User> users;

    public Promo(String name, Integer studentsNumber, Boolean isActive) {
        this.name = name;
        this.studentsNumber = studentsNumber;
        this.isActive = isActive;
    }
}
