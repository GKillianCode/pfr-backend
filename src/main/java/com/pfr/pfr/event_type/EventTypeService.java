package com.pfr.pfr.event_type;

import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.repository.EventTypeRepository;
import com.pfr.pfr.event_type.dto.EventTypeDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
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

    public EventType saveEventType(EventType eventType) throws InstanceAlreadyExistsException {
        List<EventType> eventTypes = eventTypeRepository.findAll();
        if (eventTypes.contains(eventType)) {
            throw new InstanceAlreadyExistsException("EventType with name %s already exists".formatted(eventType.getName()));
        }
        return eventTypeRepository.save(eventType);
    }

    public EventType updateEventType(int eventTypeId, EventTypeDTO eventTypeDTO) throws InstanceAlreadyExistsException, EntityNotFoundException {
        Optional<EventType> eventType = eventTypeRepository.findById(eventTypeId);
        if (eventType.isPresent()) {
            EventType presentEventType = eventType.get();

            List<EventType> eventTypes = eventTypeRepository.findAll();
            boolean hasDuplicate = eventTypes.stream()
                    .anyMatch(eventTypeDTO::equalsEventType);

            if (hasDuplicate) {
                throw new InstanceAlreadyExistsException("EventType with name %d already exists".formatted(eventTypeId));
            }

            if (!eventTypeDTO.getName().isEmpty())
                presentEventType.setName(eventTypeDTO.getName());

            if (eventTypeDTO.getIsExceptionalClosure() != null)
                presentEventType.setIsExceptionalClosure(eventTypeDTO.getIsExceptionalClosure());


            return eventTypeRepository.save(eventType.get());
        }
        throw new EntityNotFoundException("EventType with ID %d not found".formatted(eventTypeId));
    }

    public EventType archivedEventType(Integer eventTypeId) {
        Optional<EventType> optionalEventType = eventTypeRepository.findById(eventTypeId);
        if (optionalEventType.isPresent()) {
            EventType eventType = optionalEventType.get();
            eventType.setIsArchived(true);
            return eventTypeRepository.save(eventType);
        }
        throw new EntityNotFoundException("EventType with ID %d not found".formatted(eventTypeId));
    }

    public List<EventType> getAllArchived() {
        return eventTypeRepository.findEventTypeByIsArchivedTrue();
    }
}
