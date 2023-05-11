package com.pfr.pfr.promo;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.exceptions.ExceptionMessage;
import com.pfr.pfr.promo.dto.PromoWithBookings;
import com.pfr.pfr.promo.dto.PromoWithEvents;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promo")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class PromoController {

    @Autowired
    private PromoService promoService;

    @GetMapping("/all")
    public List<Promo> getAllPromos() {
        return promoService.getAll();
    }

    @GetMapping("/{id}/events")
    public PromoWithEvents getPromoWithEvents(@PathVariable("id") Integer promoId) {
        return promoService.getPromoWithEvents(promoId);
    }

    @Operation(summary = "Get a promo with its bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Promo not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/{id}/bookings")
    public PromoWithBookings getPromoWithBookings(@PathVariable("id") Integer promoId) {
        return promoService.getPromoWithBookings(promoId);
    }

    @Operation(summary = "Get a promo by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "404", description = "Promo not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class }))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class })))
    })
    @GetMapping("/{id}")
    public Promo getPromoById(@PathVariable("id") Integer promoId) {
        return promoService.getPromoById(promoId);
    }
}
