package com.pfr.pfr.trainer_promo;

import com.pfr.pfr.entities.TrainerPromo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/trainerPromo")
@Validated
public class TrainerPromoController {

    @Autowired
    private TrainerPromoService trainerPromoService;

    @GetMapping("/all")
    public List<TrainerPromo> getAllTrainerPromos() { return trainerPromoService.getAll(); }
}
