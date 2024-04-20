package ru.hogwarts.school32.service;

import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    Student deleteStudent(Long id);

    Student getStudentById(Long id);

    List<Student> getAll();

    List<Student> filterStudentByAge(Integer age);

    List<Student> findByAgeBetweens(Integer min, Integer max);

    Faculty getFaculty(Long studentId);
}
