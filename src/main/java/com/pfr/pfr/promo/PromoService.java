package com.pfr.pfr.promo;

import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.repository.PromoRepository;
import com.pfr.pfr.event.EventService;
import com.pfr.pfr.promo.dto.PromoWithEvents;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoService {

    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private EventService eventService;
    public List<Promo> getAll() {
        return promoRepository.findAll();
    }

    public PromoWithEvents getPromoWithEvents(int promoId){
        Optional<Promo> promo = promoRepository.findById(promoId);
        if (promo.isPresent()){
            List<Event> events = eventService.getEventsForPromo(promoId);
            return new PromoWithEvents(promo.get(), events);
        }
        throw new EntityNotFoundException("Promo with ID %d not found".formatted(promoId));
    }
}
