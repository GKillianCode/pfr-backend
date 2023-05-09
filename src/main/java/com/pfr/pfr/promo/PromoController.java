package com.pfr.pfr.promo;

import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.Promo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/promo")
@Validated
public class PromoController {

    @Autowired
    private PromoService promoService;

    @GetMapping("/all")
    public List<Promo> getAllPromos() { return promoService.getAll(); }

}
