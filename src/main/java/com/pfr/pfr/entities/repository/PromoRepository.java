package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromoRepository  extends JpaRepository<Promo, Integer> {


    /**
     *
     * @param userId
     * @return List of Promo
     *
     * SELECT *
     * FROM `promo`
     * INNER JOIN trainer_promo
     * ON promo.id = trainer_promo.promo_id
     * INNER JOIN user
     * ON trainer_promo.user_id = user.id
     * WHERE user.id = userId;
     */
    @Query(value = "SELECT p.* FROM promo AS p INNER JOIN trainer_promo AS tp ON tp.promo_id = p.id WHERE tp.user_id = :userId", nativeQuery = true)
    List<Promo> findPromosFromUser(@Param("userId") Integer userId);

}
