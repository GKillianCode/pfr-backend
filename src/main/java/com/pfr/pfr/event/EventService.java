package com.pfr.pfr.event;

import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> getAll() { return eventRepository.findAll(); }

    public List<Event> getEventsForPromo(int promoId) {
        return eventRepository.findByPromoId(promoId);
    }
}
