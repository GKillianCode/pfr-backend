package com.pfr.pfr.entities.repository;

import com.pfr.pfr.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    List<Location> findLocationByIsArchivedFalse();

    List<Location> findLocationByIsArchivedTrue();

}
