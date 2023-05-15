package com.pfr.pfr.event_type;

import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.repository.EventTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public List<EventType> getAll() { return eventTypeRepository.findAll(); }

    public EventType getEventTypeById(Integer eventTypeId) {
        Optional<EventType> eventType = eventTypeRepository.findById(eventTypeId);
        if(eventType.isPresent()) {
            return eventType.get();
        }
        throw new EntityNotFoundException("EventType with ID %d not found".formatted(eventTypeId));
    }



}
