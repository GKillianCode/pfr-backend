package com.pfr.pfr.location;

import com.pfr.pfr.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@Validated
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/all")
    public List<Location> getAllLocations() { return locationService.getAll(); }

}
