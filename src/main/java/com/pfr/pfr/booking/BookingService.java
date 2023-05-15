package com.pfr.pfr.booking;

import com.pfr.pfr.booking.dto.BookingDTO;
import com.pfr.pfr.classroom.ClassroomService;
import com.pfr.pfr.entities.*;
import com.pfr.pfr.entities.repository.BookingRepository;
import com.pfr.pfr.event.EventService;
import com.pfr.pfr.promo.PromoService;
import com.pfr.pfr.slot.SlotService;
import com.pfr.pfr.user.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Booking> getBookingsForPromo(Integer promoId)
    {
        return bookingRepository.findByPromoId(promoId);
    }

    public Booking saveBooking(BookingDTO bookingDTO) {

        int capacityClassroom = 0; // valeur arbitraire correspondant à "illimité"
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

        // Vérifications (capacité, slot, dispo) avant ajout
        if (checkCapacityNeeded(bookingDTO)) {
            if (checkBookingSlot(bookingDTO)) {
                if (checkNoSimilarBooking(bookingDTO)) {
                    return bookingRepository.save(bookingDTO.toEntity());
                }
            }
        } else {
            throw new IllegalArgumentException("Classroom capacity toot small");
        }
        return null;
    }

    public Booking updateBooking(int bookingId, BookingDTO bookingDTO) {
        Optional<Booking> bookingToUpdate = bookingRepository.findById(bookingId);

        boolean changeOfDate = true;
        boolean changeOfSlot = true;
        boolean changeOfClassroom = true;

        if (bookingToUpdate.isPresent()){

            // Si paramètre(s) vide(s) on alimente le DTO avec les valeurs enregistrées pour les tests qui suivront
            if (bookingDTO.getBookingDate() == null) {
                bookingDTO.setBookingDate(bookingToUpdate.get().getBookingDate());
                changeOfDate = false;
            }
            if (bookingDTO.getSlotId() == null) {
                bookingDTO.setSlotId(bookingToUpdate.get().getSlotId());
                changeOfSlot = false;
            }
            if (bookingDTO.getClassroomId() == null) {
                bookingDTO.setClassroomId(bookingToUpdate.get().getClassroomId());
                changeOfClassroom = false;
            }

            // Si au moins & des paramètres change on tente la sauvegarde
            if (changeOfDate || changeOfSlot || changeOfClassroom) {
                saveBooking(bookingDTO);
            } else {
                return bookingToUpdate.get();
            }
        }

        return null;
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
        //bookingDTO.toEntity()
        List<Booking> similarBooking = bookingRepository.findByDateAndSlotAndClassroom(bookingDTO.getBookingDate(), bookingDTO.getSlotId(), bookingDTO.getClassroomId());
        // Si le créneau est déjà réservé (même date, même slot, et même classroom)
        if (similarBooking.size() > 0) {
            throw new EntityExistsException("A similar Booking already exists (same date, same slot and same classroom");
        }
        return true;
    }

    // Vérification de la capacité par rapport aux participants
    public Boolean checkCapacityNeeded(BookingDTO bookingDTO) {
        int capacityClassroom = 0; // valeur arbitraire correspondant à "illimité"
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

        if (capacityClassroom >= capacityNeeded) {
            return true;
        }
        return false;
    }

}
