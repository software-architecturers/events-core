package com.kpi.events.model.repository;

import com.kpi.events.model.Student;
import com.kpi.events.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByUser(User user);

}
