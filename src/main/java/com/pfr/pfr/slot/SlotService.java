package com.pfr.pfr.slot;

import com.pfr.pfr.entities.Slot;
import com.pfr.pfr.entities.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    public List<Slot> getAll() { return slotRepository.findAll(); }
}
