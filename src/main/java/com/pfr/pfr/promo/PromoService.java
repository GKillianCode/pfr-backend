package com.pfr.pfr.promo;

import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.repository.PromoRepository;
import com.pfr.pfr.event.EventService;
import com.pfr.pfr.promo.dto.PromoWithBookings;
import com.pfr.pfr.promo.dto.PromoDTO;
import com.pfr.pfr.promo.dto.PromoWithEvents;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class PromoService {

    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private EventService eventService;

    @Lazy
    @Autowired
    private BookingService bookingService;

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

    public PromoWithBookings getPromoWithBookings(Integer promoId)
    {
        Optional<Promo> promo = promoRepository.findById(promoId);
        if(promo.isPresent()) {
            List<Booking> bookings = bookingService.getBookingsForPromo(promoId);
            return new PromoWithBookings(promo.get(), bookings);
        }
        throw new EntityNotFoundException("Promo with ID %d not found".formatted(promoId));
    }

    public Promo getPromoById(Integer promoId) {
        Optional<Promo> promo = promoRepository.findById(promoId);
        if(promo.isPresent()) {
            return promo.get();
        }
        throw new EntityNotFoundException("Promo with ID %d not found".formatted(promoId));
    }

    public Promo savePromo(Promo promo) throws InstanceAlreadyExistsException {
        List<Promo> promoList = getPromoByExactName(promo.getName());
        if (promoList.size() > 0) {
            throw new EntityExistsException("Promo with name %s already exists".formatted(promo.getName()));
        }
        return promoRepository.save(promo);
    }

    public List<Promo> getPromoByExactName(String promoName) {
        return promoRepository.findPromoByNameEqualsIgnoreCase(promoName);
    }

    public Promo updatePromo(int promoId, PromoDTO promoDTO) {
        Optional<Promo> promo = promoRepository.findById(promoId);
        if (promo.isPresent()){
            if (promoDTO.getName() != null) {
                // Vérification que le nouveau nom n'est pas déjà existant en base
                List<Promo> promoList = getPromoByExactName(promoDTO.getName());
                if (promoList.size() > 0) {
                    throw new EntityExistsException("Promo with name %s already exists".formatted(promoDTO.getName()));
                }
                promo.get().setName(promoDTO.getName());
            }
            if (promoDTO.getStudentsNumber() != null && promoDTO.getStudentsNumber() >= 0) {
                promo.get().setStudentsNumber(promoDTO.getStudentsNumber());
            }
            if (promoDTO.getIsActive() != null) {
                promo.get().setIsActive(promoDTO.getIsActive());
            }
            return promoRepository.save(promo.get());
        }
        throw new EntityNotFoundException("Promo with ID %d not found".formatted(promoId));
    }
}
