package com.pfr.pfr.location;

import com.pfr.pfr.entities.Location;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.repository.LocationRepository;
import com.pfr.pfr.location.dto.LocationDTO;
import com.pfr.pfr.promo.dto.PromoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAll() {
        return locationRepository.findLocationByIsArchivedFalse();
    }

    public List<Location> getAllArchived() {
        return locationRepository.findLocationByIsArchivedTrue();
    }
    public Location getLocationById(Integer locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isPresent()) {
            return location.get();
        }
        throw new EntityNotFoundException("Location with ID %d not found".formatted(locationId));
    }

    public Location saveLocation(Location location) throws InstanceAlreadyExistsException {
        List<Location> locations = locationRepository.findAll();
        if (locations.contains(location)) {
            throw new InstanceAlreadyExistsException("Location with name %s already exists".formatted(location.getName()));
        }
        return locationRepository.save(location);
    }

    public Location updateLocation(int locationId, LocationDTO locationDTO) throws InstanceAlreadyExistsException {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isPresent()) {
            Location presentLocation = location.get();

            List<Location> locations = locationRepository.findAll();
            boolean hasDuplicate = locations.stream()
                    .anyMatch(locationDTO::equalsLocation);

            if (hasDuplicate) {
                throw new InstanceAlreadyExistsException("Location with name %d already exists".formatted(locationId));
            }

            if (!locationDTO.getName().isEmpty())
                presentLocation.setName(locationDTO.getName());

            if (!locationDTO.getAddress().isEmpty())
                presentLocation.setAddress(locationDTO.getAddress());

            if (!locationDTO.getZipCode().isEmpty())
                presentLocation.setZipCode(locationDTO.getZipCode());

            if (!locationDTO.getCity().isEmpty())
                presentLocation.setCity(locationDTO.getCity());

            return locationRepository.save(location.get());
        }
        throw new EntityNotFoundException("Location with ID %d not found".formatted(locationId));
    }

    public Location archivedLocation(Integer locationId) {
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setIsArchived(true);
            return locationRepository.save(location);
        }
        throw new EntityNotFoundException("Location with ID %d not found".formatted(locationId));


    }
}
