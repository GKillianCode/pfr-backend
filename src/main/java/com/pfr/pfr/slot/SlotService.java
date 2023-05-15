package com.pfr.pfr.slot;

import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.entities.repository.SlotRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    public List<Slot> getAll() { return slotRepository.findAll(); }

    public Slot getById(Integer id) {
        Optional<Slot> slot = slotRepository.findById(id);
        if (slot.isPresent()) {
            return slot.get();
        }
        throw new EntityNotFoundException("Slot with ID %d not found".formatted(id));

    }

    public List<Slot> getByDate(LocalDate date) {
        String dayInFrench = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));
        List<Slot> daySlots = slotRepository.findAll().stream().filter(slot -> slot.getWeekDay().equalsIgnoreCase(dayInFrench)).toList();
        return daySlots;
    }
}
