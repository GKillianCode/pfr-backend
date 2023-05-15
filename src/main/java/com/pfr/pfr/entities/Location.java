package com.pfr.pfr.entities;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "location")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "address", "zipCode", "city", "isArchived"})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "is_archived")
    @BooleanFlag
    private Boolean isArchived;

    public Location(String name, String address, String zipCode, String city) {
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.isArchived = false;
    }
}
