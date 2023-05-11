package com.pfr.pfr.event;

import com.pfr.pfr.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/all")
    public List<Event> getAllEvents() { return eventService.getAll(); }
}
