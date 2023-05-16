package com.pfr.pfr.slot;

import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.entities.repository.SlotRepository;
import com.pfr.pfr.slot.dto.SlotDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    public List<Slot> getAll() { return slotRepository.findSlotByIsArchivedFalse(); }

    public List<Slot> getAllArchived() { return slotRepository.findSlotByIsArchivedTrue(); }

    public Slot getById(Integer slotId) {
        Optional<Slot> slot = slotRepository.findById(slotId);
        if (slot.isPresent()) {
            return slot.get();
        }
        throw new EntityNotFoundException("Slot with ID %d not found".formatted(slotId));
    }

    public List<Slot> getByDate(LocalDate date) {
        String dayInFrench = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));
        List<Slot> daySlots = slotRepository.findAll().stream().filter(slot -> slot.getWeekDay().equalsIgnoreCase(dayInFrench)).toList();
        return daySlots;
    }

    public Slot saveSlot(Slot slot) throws InstanceAlreadyExistsException {
        List<Slot> slots = slotRepository.findAll();
        if (slots.contains(slot)) {
            throw new InstanceAlreadyExistsException("Slot with weekDay %s and daytime %s already exists".formatted(slot.getWeekDay(), slot.getDaytime()));
        }
        return slotRepository.save(slot);
    }

    public Slot updateSlot(int slotId, SlotDTO slotDTO) throws InstanceAlreadyExistsException {
        Optional<Slot> slot = slotRepository.findById(slotId);
        if (slot.isPresent()) {
            Slot presentSlot = slot.get();

            List<Slot> slots = slotRepository.findAll();
            boolean hasDuplicate = slots.stream()
                    .anyMatch(slotDTO::equalsSlot);

            if (hasDuplicate) {
                throw new InstanceAlreadyExistsException("Slot with weekDay %s and daytime %s already exists".formatted(presentSlot.getWeekDay(), presentSlot.getDaytime()));
            }

            if (!slotDTO.getWeekDay().isEmpty())
                presentSlot.setWeekDay(slotDTO.getWeekDay());

            if (!slotDTO.getDaytime().isEmpty())
                presentSlot.setDaytime(slotDTO.getDaytime());

            if (slotDTO.getIsBookable() != null) {
                presentSlot.setIsBookable(slotDTO.getIsBookable());
            }

            return slotRepository.save(slot.get());
        }
        throw new EntityNotFoundException("Slot with ID %d not found".formatted(slotId));
    }

    public Slot archivedSlot(Integer slotId) {
        Optional<Slot> optionalSlot = slotRepository.findById(slotId);
        if (optionalSlot.isPresent()) {
            Slot slot = optionalSlot.get();
            slot.setIsArchived(true);
            return slotRepository.save(slot);
        }
        throw new EntityNotFoundException("Slot with ID %d not found".formatted(slotId));
    }
}
