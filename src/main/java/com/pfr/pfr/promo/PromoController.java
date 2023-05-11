package com.pfr.pfr.promo;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.promo.dto.PromoDTO;
import com.pfr.pfr.promo.dto.PromoWithEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/promo")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class PromoController {

    @Autowired
    private PromoService promoService;

    @GetMapping("/all")
    public List<Promo> getAllPromos() { return promoService.getAll(); }

    @GetMapping("/{id}")
    public Promo getPromoById(@PathVariable("id") Integer promoId) {
        return promoService.getById(promoId);
    }

    @GetMapping("/{id}/events")
    public PromoWithEvents getPromoWithEvents(@PathVariable("id") Integer promoId) {
        return promoService.getPromoWithEvents(promoId);
    }

    @PostMapping("")
    public ResponseEntity<Promo> savePromo(@RequestBody Promo newPromo) throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(promoService.savePromo(newPromo));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Promo> updatePromo(
            @PathVariable("id") Integer promoId ,
            @RequestBody PromoDTO promoDTO
    ) throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(promoService.updatePromo(promoId, promoDTO));
    }
}
