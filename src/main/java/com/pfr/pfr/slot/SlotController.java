package com.pfr.pfr.slot;

import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.exceptions.ExceptionMessage;
import com.pfr.pfr.slot.dto.SlotDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/slot")
@Tag(name = "Slot")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
@SecurityRequirement(name = "Authorization")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @Operation(summary = "Get all slots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Slot.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Slots not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/all")
    public List<Slot> getAllSlots() { return slotService.getAll(); }

    @Operation(summary = "Get slot by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Slot.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Slot not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/{id}")
    public Slot getSlotById(@PathVariable("id") Integer slotId) {
        return slotService.getById(slotId);
    }

    @Operation(summary = "Save slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Slot.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "409", description = "Slot already exists", content = @Content(mediaType = "application/json",  schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })
    @PostMapping("/save")
    public ResponseEntity<Slot> saveSlot(@RequestBody Slot slot) throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(slotService.saveSlot(slot));
    }

    @Operation(summary = "Update slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Slot.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "409", description = "Slot with same name already exists", content = @Content(mediaType = "application/json",  schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Slot> updateSlot(
            @PathVariable("id") Integer slotId ,
            @RequestBody SlotDTO slotDTO
    ) throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(slotService.updateSlot(slotId, slotDTO));
    }

    @Operation(summary = "Archived slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Slot.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })
    @PatchMapping("/{id}/archived")
    public ResponseEntity<Slot> archivedSlot(@PathVariable("id") Integer slotId)
            throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(slotService.archivedSlot(slotId));
    }


    @Operation(summary = "Get all archived slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Slot.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
    })
    @GetMapping("/all/archived")
    public List<Slot> getAllArchivedSlots() { return slotService.getAllArchived(); }


}
