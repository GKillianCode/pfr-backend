package com.pfr.pfr.event;

import com.pfr.pfr.entities.Event;
import com.pfr.pfr.event.dto.EventDTO;
import com.pfr.pfr.event.dto.EventWithBookings;
import com.pfr.pfr.exceptions.ExceptionMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class EventController {

    @Autowired
    EventService eventService;

    @Operation(summary = "Get all events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Events not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/all")
    public List<Event> getAllEvents() { return eventService.getAll(); }

    @Operation(summary = "Get all bookings for event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Events not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/{id}/bookings")
    public EventWithBookings getEventWithBookings(@PathVariable("id") Integer eventId) {
        return eventService.getEventWithBookings(eventId);
    }

    @Operation(summary = "add a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "409", description = "Event with same name already exists", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @PostMapping("")
    public ResponseEntity<Event> saveEvent(@RequestBody Event newEvent) throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(eventService.saveEvent(newEvent));
    }

    @Operation(summary = "update an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "409", description = "Event with same name already exists", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable("id") Integer eventId,
            @RequestBody EventDTO eventDTO)
            throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(eventService.updateEvent(eventId, eventDTO));
    }

    @Operation(summary = "archive an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "409", description = "Event with same name already exists", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @PatchMapping("{id}/archived")
    public ResponseEntity<Event> archivedEvent(@PathVariable("id") Integer eventId) throws EntityNotFoundException {
        return ResponseEntity.ok(eventService.archivedEvent(eventId));
    }


    @Operation(summary = "Get all archived events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Archived events not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/all/archived")
    List<Event> getAllArchivedEvents() { return eventService.getAllArchived(); }
}
