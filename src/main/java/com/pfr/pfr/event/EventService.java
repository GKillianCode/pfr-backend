package com.pfr.pfr.event;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.entities.repository.BookingRepository;
import com.pfr.pfr.entities.repository.EventRepository;
import com.pfr.pfr.event.dto.EventWithBookings;
import com.pfr.pfr.user.dto.UserWithBookings;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    BookingRepository bookingRepository;

    public List<Event> getAll() { return eventRepository.findAll(); }

    public List<Event> getEventsForPromo(int promoId) {
        return eventRepository.findByPromoId(promoId);
    }

    public EventWithBookings getEventWithBookings(int eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            List<Booking> listBooking = bookingRepository.findAllByEventId(eventId);
            return new EventWithBookings(event.get(), listBooking);
        }
        throw new EntityNotFoundException("Event with ID %d not found".formatted(eventId));

    }
}
