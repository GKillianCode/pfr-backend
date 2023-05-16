package com.pfr.pfr.event_type;

import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.entities.EventType;
import com.pfr.pfr.event_type.dto.EventTypeDTO;
import com.pfr.pfr.exceptions.ExceptionMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/event-type")
@Tag(name = "Event Type")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @Operation(summary = "Get all event types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventType.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Event types not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/all")
    public List<EventType> getAllEventTypes() { return eventTypeService.getAll(); }


    @Operation(summary = "Get event type by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventType.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Event type not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/{id}")
    public EventType getEventTypeById(@PathVariable("id") Integer eventTypeId) {
        return eventTypeService.getEventTypeById(eventTypeId);
    }

    @Operation(summary = "Save event type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventType.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "409", description = "EventType already exists", content = @Content(mediaType = "application/json",  schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })
    @PostMapping("/save")
    public ResponseEntity<EventType> saveEventType(@RequestBody EventType newEventType) throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(eventTypeService.saveEventType(newEventType));
    }

    @Operation(summary = "Update event type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventType.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "409", description = "EventType with same name already exists", content = @Content(mediaType = "application/json",  schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Event type not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EventType> updateEventType(
            @PathVariable("id") Integer eventTypeId ,
            @RequestBody EventTypeDTO eventTypeDTO
    ) throws InstanceAlreadyExistsException, EntityNotFoundException {
        return ResponseEntity.ok(eventTypeService.updateEventType(eventTypeId, eventTypeDTO));
    }

    @Operation(summary = "Archived event type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventType.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Event type not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })

    @PatchMapping("/{id}/archived")
    public ResponseEntity<EventType> archivedEventType(@PathVariable("id") Integer eventTypeId)
            throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(eventTypeService.archivedEventType(eventTypeId));
    }


    @Operation(summary = "Get all archived event type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventType.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })
    @GetMapping("/all/archived")
    public List<EventType> getAllArchivedEventTypes() { return eventTypeService.getAllArchived(); }

}
