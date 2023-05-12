package com.pfr.pfr.classroom;

import com.pfr.pfr.booking.BookingService;
import com.pfr.pfr.classroom.dto.ClassroomWithBookings;
import com.pfr.pfr.entities.Booking;
import com.pfr.pfr.entities.Classroom;
import com.pfr.pfr.entities.repository.ClassroomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    //@Lazy
    @Autowired
    private BookingService bookingService;

    public List<Classroom> getAll() { return classroomRepository.findAll(); }

    public List<Integer> getAllDistinctCapacities() {
        return classroomRepository.findDistinctCapacitiesOrderByCapacityAsc();
    }

    public List<Classroom> getClassroomsByLocationId(Integer locationId) {
        return classroomRepository.findByLocationId(locationId);
    }

    public List<Classroom> getClassroomsByCapacity(Integer capacity) {
        return classroomRepository.findByCapacity(capacity);
    }

    public List<Classroom> filterClassroom(Classroom classroom){
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return classroomRepository.findAll(Example.of(classroom, exampleMatcher));
    }

    public ClassroomWithBookings getClassroomWithBookings(Integer classroomId) {
        Optional<Classroom> classroom = classroomRepository.findById(classroomId);
        if(classroom.isPresent()) {
            List<Booking> listBookings = bookingService.getBookingsByClassroom(classroomId);
            return new ClassroomWithBookings(classroom.get(), listBookings);
        }
        throw new EntityNotFoundException("Classroom with ID %d not found".formatted(classroomId));
    }
}
