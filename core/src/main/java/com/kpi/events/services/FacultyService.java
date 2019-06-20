package com.kpi.events.services;

import com.kpi.events.model.Faculty;
import com.kpi.events.model.Student;
import com.kpi.events.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService implements IService<Faculty> {

    private static final String STUDENT_NOT_FOUND = "Student with this id is not present";

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Faculty> findAll(int size, int page) {
        return null;
    }

    @Override
    public Faculty save(Faculty entity) {
        return null;
    }

    @Override
    public Faculty find(long id) {
        return null;
    }

    @Override
    public Faculty update(long id, Faculty entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    public Faculty findByStudentId(long studentId) {
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(STUDENT_NOT_FOUND));

        return student.getFaculty();
    }
    
}
