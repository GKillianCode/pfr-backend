package com.pfr.pfr.user.dto;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.User;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"user", "promos"})
public class UserWithPromo {
    private User user;
    private List<Promo> promos;


}
