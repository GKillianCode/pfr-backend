package com.pfr.pfr.booking;

import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAll() { return bookingRepository.findAll(); }

    public List<Booking> getBookingsForPromo(Integer promoId)
    {
        return bookingRepository.findByPromoId(promoId);
    }
}
