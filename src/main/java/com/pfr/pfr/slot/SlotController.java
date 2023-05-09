package com.pfr.pfr.slot;

import com.pfr.pfr.entities.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/slot")
@Validated
public class SlotController {

    @Autowired
    private SlotService slotService;

    @GetMapping("/all")
    public List<Slot> getAllSlots() { return slotService.getAll(); }
}
