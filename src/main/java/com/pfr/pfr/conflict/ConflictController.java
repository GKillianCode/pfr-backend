package com.pfr.pfr.conflict;

import com.pfr.pfr.entities.Conflict;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conflict")
@Tag(name = "Conflict")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class ConflictController {

    @Autowired
    private ConflictService conflictService;

    @GetMapping("/all")
    public List<Conflict> getAllConflicts() { return conflictService.getAll(); }

}
