package com.kpi.events.services;

import com.kpi.events.model.Hostel;
import com.kpi.events.model.Student;
import com.kpi.events.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HostelService implements IService {

    private static final String STUDENT_NOT_FOUND = "Student with this id is not present";

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Hostel findByStudentId(long studentId) {
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(()->
                        new IllegalArgumentException(STUDENT_NOT_FOUND));

        return student.getHostel();
    }

    @Override
    public List findAll(int size, int page) {
        return null;
    }

    @Override
    public Hostel save(Object entity) {
        return null;
    }

    @Override
    public Hostel find(long userid) {
        return null;
    }

    @Override
    public Hostel update(long id, Object entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
