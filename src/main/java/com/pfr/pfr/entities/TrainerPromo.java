package com.pfr.pfr.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trainer_promo")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"user_id", "promo_id"})
public class TrainerPromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "promo_id", updatable = true, insertable = true)
    private Promo promo;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = true, insertable = true)
    private User user;

    public TrainerPromo(Promo promo, User user) {
        this.promo = promo;
        this.user = user;
    }
}
