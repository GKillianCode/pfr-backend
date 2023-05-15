package com.pfr.pfr.location.dto;

import com.pfr.pfr.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.jfr.BooleanFlag;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "address", "zipCode", "city", "isArchived"})
public class LocationDTO {

    private String name;

    private String address;

    private String zipCode;

    private String city;

    @BooleanFlag
    private Boolean isArchived;

    public LocationDTO(String name, String address, String zipCode, String city) {
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.isArchived = false;
    }

    public boolean equalsLocation(Location location) {
        return Objects.equals(name, location.getName());
    }

}
