package com.pfr.pfr.event_type;

import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.Location;
import com.pfr.pfr.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event-type")
@Validated
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @GetMapping("/all")
    public List<EventType> getAllEventTypes() { return eventTypeService.getAll(); }
}
