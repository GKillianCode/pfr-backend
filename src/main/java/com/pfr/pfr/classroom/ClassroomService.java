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

import javax.management.InstanceAlreadyExistsException;
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

    public Classroom saveClassroom(Classroom classroom) throws InstanceAlreadyExistsException {
        List<Classroom> classroomList = getClassroomByExactName(classroom.getName());
        if (classroomList.size() > 0) {
            throw new InstanceAlreadyExistsException("Classroom with name %s already exists".formatted(classroom.getName()));
        }
        return classroomRepository.save(classroom);
    }

    public List<Classroom> getClassroomByExactName(String classroomName) {
        return classroomRepository.findClassroomByNameEqualsIgnoreCase(classroomName);
    }

    public Classroom updateClassroom(int classroomId, Classroom classroomDTO) throws InstanceAlreadyExistsException {
        Optional<Classroom> classroom = classroomRepository.findById(classroomId);
        if (classroom.isPresent()){
            if (classroomDTO.getName() != null) {
                // Vérification que le nouveau nom n'est pas déjà existant en base
                List<Classroom> classroomList = getClassroomByExactName(classroomDTO.getName());
                if (classroomList.size() > 0) {
                    throw new InstanceAlreadyExistsException("Classroom with name %s already exists".formatted(classroomDTO.getName()));
                }
                classroom.get().setName(classroomDTO.getName());
            }
            if (classroomDTO.getCapacity() != null && classroomDTO.getCapacity() >= 0) {
                classroom.get().setCapacity(classroomDTO.getCapacity());
            }
            if (classroomDTO.getLocation() != null) {
                classroom.get().setLocation(classroomDTO.getLocation());
            }
            if (classroomDTO.getIsBookable() != null) {
                classroom.get().setIsBookable(classroomDTO.getIsBookable());
            }

            return classroomRepository.save(classroom.get());
        }
        throw new EntityNotFoundException("Classroom with ID %d not found".formatted(classroomId));
    }

    public void deleteClassroom(int classroomId) {
        Optional<Classroom> classroom = classroomRepository.findById(classroomId);
        if (classroom.isPresent()){
            classroomRepository.delete(classroom.get());
        }
        else {
            throw new EntityNotFoundException("Classroom with ID %d not found".formatted(classroomId));
        }
    }

    public void archiveClassroom(int classroomId){
        Optional<Classroom> classroom = classroomRepository.findById(classroomId);
        if (classroom.isPresent()){
            classroom.get().setIsArchived(!classroom.get().getIsArchived());
            classroomRepository.save(classroom.get());
        }
        else {
            throw new EntityNotFoundException("Classroom with ID %d not found".formatted(classroomId));
        }
    }
}
