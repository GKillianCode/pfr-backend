package com.pfr.pfr.location;

import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAll() { return locationRepository.findAll(); }

}
