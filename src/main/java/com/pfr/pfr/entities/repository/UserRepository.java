package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    //@Query("select p from Promo p inner join UPPER(b.title) like UPPER(?1)")
//    List<Book> findByBooksName(String name);

    // SELECT *
    // FROM `promo`
    // INNER JOIN trainer_promo
    // ON promo.id = trainer_promo.promo_id
    // INNER JOIN user
    // ON trainer_promo.user_id = user.id
    // WHERE user.id = 1;

    //@Query("select p from Promo p inner join trainer_promo tp where tp.user_id = :userId")
    @Query(value = "select p.* from promo as p inner join trainer_promo as tp on tp.promo_id = p.id where tp.user_id = :userId", nativeQuery = true)
    List<Promo> findPromosFromUser(@Param("userId") Integer userId);

}
