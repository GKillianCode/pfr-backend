package com.pfr.pfr.event;

import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> getAll() { return eventRepository.findAll(); }

    public Event getById(int eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            return event.get();
        }
        throw new EntityNotFoundException("Promo with ID %d not found".formatted(eventId));
    }

    public List<Event> getEventsForPromo(int promoId) {
        return eventRepository.findByPromoId(promoId);
    }
}
