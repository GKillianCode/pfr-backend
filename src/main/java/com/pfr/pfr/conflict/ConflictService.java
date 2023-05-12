package com.pfr.pfr.conflict;

import com.pfr.pfr.entities.Conflict;
import com.pfr.pfr.entities.repository.ConflictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConflictService {

    @Autowired
    private ConflictRepository conflictRepository;

    public List<Conflict> getAll() { return conflictRepository.findAll(); }

    public List<Conflict> getConflictsForBooking(Integer bookingId) {
        return conflictRepository.findByBookingId(bookingId);
    }
}
