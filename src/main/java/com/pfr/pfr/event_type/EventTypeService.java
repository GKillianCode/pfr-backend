package com.pfr.pfr.event_type;

import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public List<EventType> getAll() { return eventTypeRepository.findAll(); }

}
