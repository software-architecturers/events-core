package com.kpi.events.services;

import com.kpi.events.model.Student;
import com.kpi.events.model.StudentIn;
import com.kpi.events.model.User;
import com.kpi.events.model.dto.StudentDto;
import com.kpi.events.model.repository.StudentRepository;
import com.kpi.events.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService implements IService<Student> {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Student> findAll(int size, int page) {
        return repository.findAll();
    }

    @Override
    public Student save(Student entity) {
        return null;
    }

    @Override
    public Student find(long id) {
        return null;
    }

    @Override
    public Student update(long id, Student entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Transactional
    public StudentDto loginStudent(StudentIn student) {
        String login = student.getLogin();
        String password = student.getPassword();

        User user = userRepository.findByLoginAndPassword(login, password);
//        if (user == null) {
//            return null;
//        }

        Student studentByUser = repository.findStudentByUser(user);
        StudentDto studentDto = new StudentDto();
        studentDto.setUserId(user.getId());
        studentDto.setStudentId(studentByUser.getId());
        studentDto.setFirstName(studentByUser.getUser().getFirstName());
        studentDto.setSecondName(studentByUser.getUser().getSecondName());
        studentDto.setFacultyNumber(studentByUser.getFaculty().getFacultyNumber());
        studentDto.setHostelNumber(studentByUser.getHostel().getHostelNumber());

        return studentDto;

    }
}
