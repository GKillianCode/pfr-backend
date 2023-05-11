package com.pfr.pfr.user.dto;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.User;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"user", "promos"})
public class UserWithPromos {
    private User user;
    private List<Promo> promos;

    public UserWithPromos(User user, List<Promo> promos) {
        this.user = user;
        this.promos = promos;
    }
}
