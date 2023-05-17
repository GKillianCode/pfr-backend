package com.pfr.pfr.conflict;

import com.pfr.pfr.entities.Conflict;
import com.pfr.pfr.entities.repository.ConflictRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class ConflictService {

    @Autowired
    private ConflictRepository conflictRepository;

    public List<Conflict> getAll() { return conflictRepository.findAll(); }

    public List<Conflict> getConflictsForBooking(Integer bookingId) {
        return conflictRepository.findByBookingId(bookingId);
    }

    public Conflict getById(Integer id) throws EntityNotFoundException {
        Optional<Conflict> conflict = conflictRepository.findById(id);
        if(conflict.isPresent()) {
            return conflict.get();
        } else {
            throw new EntityNotFoundException("No conflict found with id " + id);
        }
    }

    public Conflict save(Conflict conflict) throws InstanceAlreadyExistsException {
        List<Conflict> conflicts = conflictRepository.findByBookingId(conflict.getId());
            if(conflicts.contains(conflict)){
                throw new InstanceAlreadyExistsException("Conflict already exists");
            }
        return conflictRepository.save(conflict);
    }

    public void delete(Integer id) throws EntityNotFoundException {
        Optional<Conflict> conflict = conflictRepository.findById(id);
        if(conflict.isPresent()) {
            conflictRepository.delete(conflict.get());
        } else {
            throw new EntityNotFoundException("No conflict found with id " + id);
        }
    }
}
