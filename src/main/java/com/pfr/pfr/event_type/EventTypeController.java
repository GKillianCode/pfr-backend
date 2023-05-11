package com.pfr.pfr.event_type;

import com.pfr.pfr.entities.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event-type")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @GetMapping("/all")
    public List<EventType> getAllEventTypes() { return eventTypeService.getAll(); }
}
