package com.pfr.pfr.promo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
public class PromoDTO {
    private String name;

    private Integer studentsNumber;

    private Boolean isActive;
}
