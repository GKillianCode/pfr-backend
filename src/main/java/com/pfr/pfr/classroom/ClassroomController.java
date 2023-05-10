package com.pfr.pfr.classroom;

import com.pfr.pfr.entities.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/classroom")
@Validated
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/all")
    public List<Classroom> getAllClassrooms() { return classroomService.getAll(); }

    @GetMapping("/capacities")
    public List<Integer> getAllDistinctCapacities() {
        return classroomService.getAllDistinctCapacities();
    }

    @GetMapping("/all/filter/location")
    public List<Classroom> getClassroomsByLocation(@RequestParam Integer id)
    {
        return classroomService.getClassroomsByLocationId(id);
    }

    @GetMapping("/all/filter/capacity")
    public List<Classroom> getClassroomsByCapacity(@RequestParam Integer number)
    {
        return classroomService.getClassroomsByCapacity(number);
    }
}
