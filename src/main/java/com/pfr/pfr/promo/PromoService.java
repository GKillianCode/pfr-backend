package com.pfr.pfr.promo;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoService {

    @Autowired
    private PromoRepository promoRepository;

    public List<Promo> getAll() {
        return promoRepository.findAll();
    }
}
