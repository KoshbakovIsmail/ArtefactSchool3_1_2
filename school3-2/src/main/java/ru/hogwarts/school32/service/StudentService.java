package ru.hogwarts.school32.service;

import ru.hogwarts.school32.model.Student;

import java.util.Map;

public interface StudentService {
    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    Student deleteStudent(Long id);

    Student getStudentById(Long id);

    Map<Long, Student> getAll();

    Map<Long, Student> filterStudentByAge(Integer age);

}
