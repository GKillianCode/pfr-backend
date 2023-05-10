package com.pfr.pfr.classroom;

import com.pfr.pfr.entities.Classroom;
import com.pfr.pfr.entities.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public List<Classroom> getAll() { return classroomRepository.findAll(); }

}
