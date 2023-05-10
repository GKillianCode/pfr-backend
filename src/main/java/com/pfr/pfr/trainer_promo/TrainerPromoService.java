package com.pfr.pfr.trainer_promo;

import com.pfr.pfr.entities.TrainerPromo;
import com.pfr.pfr.entities.repository.TrainerPromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerPromoService {

    @Autowired
    private TrainerPromoRepository trainerPromoRepository;

    public List<TrainerPromo> getAll() { return trainerPromoRepository.findAll(); }
}
