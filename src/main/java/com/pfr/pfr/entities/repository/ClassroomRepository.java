package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    @Query("SELECT DISTINCT c.capacity FROM Classroom c ORDER BY c.capacity ASC")
    List<Integer> findDistinctCapacitiesOrderByCapacityAsc();

    List<Classroom> findByLocationId(Integer locationId);
    List<Classroom> findByCapacity(Integer capacity);

    List<Classroom> findClassroomByNameEqualsIgnoreCase(String name);

    Classroom findClassroomById(Integer id);

    // Get all active classrooms
    List<Classroom> findClassroomByIsArchivedFalse();

    // Get all archived classrooms
    List<Classroom> findClassroomByIsArchivedTrue();


}
