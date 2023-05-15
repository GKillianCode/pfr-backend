package com.pfr.pfr.booking;

import com.pfr.pfr.booking.dto.BookingWithConflicts;
import com.pfr.pfr.conflict.ConflictService;
import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Conflict;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.entities.repository.BookingRepository;
import com.pfr.pfr.promo.dto.PromoWithBookings;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ConflictService conflictService;

    public List<Booking> getAll() { return bookingRepository.findAll(); }

    public List<Booking> getBookingsByClassroom(Integer classroomId) 
    { 
      return bookingRepository.findByClassroomId(classroomId); 
    }
    public List<Booking> getBookingsForPromo(Integer promoId)
    {
        return bookingRepository.findByPromoId(promoId);
    }

    public BookingWithConflicts getBookingWithConflicts(Integer bookingId)
    {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isPresent()) {
            List<Conflict> conflicts = conflictService.getConflictsForBooking(bookingId);
            return new BookingWithConflicts(booking.get(), conflicts);
        }
        throw new EntityNotFoundException("Booking with ID %d not found".formatted(bookingId));
    }


}
