package com.pfr.pfr.event;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Event;
import com.pfr.pfr.entities.repository.BookingRepository;
import com.pfr.pfr.entities.repository.EventRepository;
import com.pfr.pfr.event.dto.EventDTO;
import com.pfr.pfr.event.dto.EventWithBookings;
import com.pfr.pfr.event_type.EventTypeService;
import com.pfr.pfr.promo.PromoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EventTypeService eventTypeService;

    @Autowired
    PromoService promoService;

    public List<Event> getAll() { return eventRepository.findEventByIsArchivedFalse(); }

    public List<Event> getAllArchived() { return eventRepository.findEventByIsArchivedTrue(); }

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

    public Event saveEvent(Event newEvent) throws InstanceAlreadyExistsException {
        List<Event> eventList = getEventByExactName(newEvent.getName());
        if(eventList.size() > 0) {
            throw new InstanceAlreadyExistsException("Event with name %s already exists".formatted(newEvent.getName()));
        }
        return eventRepository.save(newEvent);
    }

    public List<Event> getEventByExactName(String eventName) {
        return eventRepository.findEventByNameEqualsIgnoreCase(eventName);
    }

    public Event updateEvent(Integer eventId, EventDTO eventDTO) throws InstanceAlreadyExistsException {
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isPresent()) {
            if(eventDTO.getName() != null) {
                List<Event> eventList = getEventByExactName(eventDTO.getName());
                if (eventList.size() > 0) {
                    throw new InstanceAlreadyExistsException("Event with name %s already exists".formatted(eventDTO.getName()));
                }
                event.get().setName(eventDTO.getName());
            }
            if(eventDTO.getSpeakerFirstname() != null) {
                event.get().setSpeakerFirstname(eventDTO.getSpeakerFirstname());
            }
            if(eventDTO.getSpeakerLastName() != null) {
                event.get().setSpeakerLastName(eventDTO.getSpeakerLastName());
            }
            if(eventDTO.getSpeakerEmail() != null) {
                event.get().setSpeakerEmail(eventDTO.getSpeakerEmail());
            }
            if(eventDTO.getSpeakerPhoneNumber() != null) {
                event.get().setSpeakerPhoneNumber(eventDTO.getSpeakerPhoneNumber());
            }
            if(eventDTO.getDescription() != null) {
                event.get().setDescription(eventDTO.getDescription());
            }
            if(eventDTO.getParticipantsNumber() != null) {
                event.get().setParticipantsNumber(eventDTO.getParticipantsNumber());
            }
            if(eventDTO.getEventTypeId() != null) {
                eventTypeService.getEventTypeById(eventDTO.getEventTypeId());
            }
            if(eventDTO.getPromoId() != null) {
                promoService.getPromoById(eventDTO.getPromoId());
            }
            return eventRepository.save(event.get());
        }
        throw new EntityNotFoundException("Event with ID %d not found".formatted(eventId));
    }

    public Event archivedEvent(Integer eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if(optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setIsArchived(true);
            return eventRepository.save(event);
        }
        throw new EntityNotFoundException("Event with ID %d not found".formatted(eventId));
    }
}
