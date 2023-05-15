package com.pfr.pfr.classroom;

import com.pfr.pfr.entities.Classroom;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.entities.repository.ClassroomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

    public List<Classroom> getAll() { return classroomRepository.findAll(); }

    public Classroom getById(int id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);
        if (classroom.isPresent()) {
            return classroom.get();
        }
        throw new EntityNotFoundException("Classroom with ID %d not found".formatted(id));
    }

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

}
