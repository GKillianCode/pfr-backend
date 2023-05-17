package com.pfr.pfr.booking;

import com.pfr.pfr.booking.dto.BookingWithConflicts;
import com.pfr.pfr.conflict.ConflictService;
import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Conflict;
import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.booking.dto.BookingDTO;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.entities.*;
import com.pfr.pfr.entities.repository.BookingRepository;
import com.pfr.pfr.promo.dto.PromoWithBookings;
import jakarta.persistence.EntityNotFoundException;
import com.pfr.pfr.event.EventService;
import com.pfr.pfr.promo.PromoService;
import com.pfr.pfr.slot.SlotService;
import com.pfr.pfr.user.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ConflictService conflictService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PromoService promoService;

    @Autowired
    private SlotService slotService;

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

    public Booking saveBooking(BookingDTO bookingDTO) {

        // Vérifications (capacité, slot, dispo) avant ajout
        if (checkCapacityNeeded(bookingDTO)) {
            if (checkBookingSlot(bookingDTO)) {
                if (checkNoSimilarBooking(bookingDTO)) {
                    return bookingRepository.save(bookingDTO.toEntity());
                }
            }
        }
        return null;
    }

    public Booking updateBooking(int bookingId, BookingDTO bookingDTO) {
        Optional<Booking> bookingToUpdate = bookingRepository.findById(bookingId);

        if (bookingToUpdate.isPresent()) {

            // Les paramètres du DTO qui sont vides ou à null du DTO sont alimentés par ceux du bookingToUpdate (celui enregistré qu'on veut modifier)
            bookingDTO.setBookingDate(Optional.ofNullable(bookingDTO.getBookingDate()).orElseGet(() -> bookingToUpdate.get().getBookingDate()));
            bookingDTO.setSlotId(Optional.ofNullable(bookingDTO.getSlotId()).orElseGet(() -> bookingToUpdate.get().getSlotId()));
            bookingDTO.setClassroomId(Optional.ofNullable(bookingDTO.getClassroomId()).orElseGet(() -> bookingToUpdate.get().getClassroomId()));
            bookingDTO.setEventId(Optional.ofNullable(bookingDTO.getEventId()).orElseGet(() -> bookingToUpdate.get().getEventId()));
            bookingDTO.setUserId(Optional.ofNullable(bookingDTO.getUserId()).orElseGet(() -> bookingToUpdate.get().getUserId()));

            // Vérifications (capacité, slot, dispo) avant ajout
            if (checkCapacityNeeded(bookingDTO)) {
                if (checkBookingSlot(bookingDTO)) {
                    if (checkNoSimilarBooking(bookingDTO)) {
                        Booking b = bookingDTO.toEntity();
                        bookingToUpdate.get().setBookingDate(b.getBookingDate());
                        bookingToUpdate.get().setSlotId(b.getSlotId());
                        bookingToUpdate.get().setClassroomId(b.getClassroomId());
                        bookingToUpdate.get().setEventId(b.getEventId());
                        bookingToUpdate.get().setUserId(b.getUserId());
                        return bookingRepository.save(bookingToUpdate.get());
                    }
                }
            }

        } else {
            throw new EntityNotFoundException("Booking with ID %d not found".formatted(bookingId));
        }
        return null;
    }

    public void deleteBooking(int bookingId) {
        Optional<Booking> bookingToDelete = bookingRepository.findById(bookingId);
        List<Conflict> conflictList = conflictService.getConflictsForBooking(bookingId);

        if (bookingToDelete.isPresent()) {
            if (conflictList.size() == 0) {
                bookingRepository.deleteById(bookingId);
            } else {
                throw new DataIntegrityViolationException("Booking cannot be deleted because it has conflict(s)");
            }

        } else {
            throw new EntityNotFoundException("Booking with ID %d not found".formatted(bookingId));
        }
    }

    // Vérification existence du slot à la date et s'il est autorisé à la réservation
    public Boolean checkBookingSlot(BookingDTO bookingDTO) {
        // Liste des slots correspondants à la date
        List<Slot> slots = slotService.getByDate(bookingDTO.getBookingDate());
        // Le slot désiré
        Slot slot = slotService.getById(bookingDTO.getSlotId());

        // Si le slot du booking ne correspond pas à la date (exemple date correspondant à un lundi et slot correspondant à un mardi)
        if (!slots.contains(slot)) {
            throw new IllegalArgumentException("Slot not matching date");
        }
        // Si le slot n'est pas autorisé à la réservation
        if (!slot.getIsBookable()) {
            throw new IllegalArgumentException("Slot not bookable");
        }
        return true;
    }

    // Vérification d'un éventuel booking similaire
    public Boolean checkNoSimilarBooking(BookingDTO bookingDTO) {
        List<Booking> similarBooking = bookingRepository.findByDateAndSlotAndClassroom(bookingDTO.getBookingDate(), bookingDTO.getSlotId(), bookingDTO.getClassroomId());
        // Si le créneau est déjà réservé (même date, même slot, et même classroom)
        if (similarBooking.size() > 0) {
            throw new EntityExistsException("A similar Booking already exists (same date, same slot and same classroom");
        }
        return true;
    }

    // Vérification de la capacité par rapport aux participants
    public Boolean checkCapacityNeeded(BookingDTO bookingDTO) {
        int capacityClassroom = 1000; // valeur arbitraire correspondant à "illimité"
        int capacityNeeded = 0;

        // Récupération de la capacité nécessaire (ordre priorité : 1-event.participantsNumbrer, 2-promo.studentsNumber)
        Event event = eventService.getById(bookingDTO.getEventId());
        if (event.getParticipantsNumber() != null) {
            capacityNeeded = event.getParticipantsNumber();
        } else {
            if (event.getPromo() != null) {
                capacityNeeded = event.getPromo().getStudentsNumber();
            }
        }

        // Si classroom renseignée et pas de nombre de participants spécifié
        if (bookingDTO.getClassroomId() != null && event.getParticipantsNumber() == null) {
            capacityClassroom = classroomService.getById(bookingDTO.getClassroomId()).getCapacity();
        }

        if (capacityClassroom < capacityNeeded) {
            throw new IllegalArgumentException("Classroom capacity too small");
        }
        return true;
    }

}
