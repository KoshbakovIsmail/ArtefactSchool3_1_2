package ru.hogwarts.school32.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.StudentNotFoundException;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.repositorys.RepositoryStudent;
import ru.hogwarts.school32.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final RepositoryStudent repositoryStudent;

    public StudentServiceImpl(RepositoryStudent repositoryStudent) {
        this.repositoryStudent = repositoryStudent;
    }

    @Override
    public Student createStudent(Student student) {
        return repositoryStudent.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student updateStudent = repositoryStudent.findById(id).orElse(null);
        if (updateStudent != null) {
            updateStudent.setId(id);
            updateStudent.setName(student.getName());
            updateStudent.setAge(student.getAge());
            return repositoryStudent.save(updateStudent);
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Student deleteStudent(Long id) {
        Student deletedStudent = repositoryStudent.findById(id).orElseThrow(() -> new StudentNotFoundException());
        repositoryStudent.deleteById(id);
        return deletedStudent;
    }

    @Override
    public Student getStudentById(Long id) {
        return repositoryStudent.findById(id).orElseThrow(() -> new StudentNotFoundException());
    }

    @Override
    public List<Student> getAll() {
        return repositoryStudent.findAll();
    }

    @Override
    public List<Student> filterStudentByAge(Integer age) {
        return repositoryStudent.findByAge(age);
    }

    @Override
    public List<Student> findByAgeBetweens(Integer min, Integer max) {
        return repositoryStudent.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFaculty(Long studentId) {
        Student student = getStudentById(studentId);
        return student.getFaculty();
    }

    @Override
    public int getStudentCount() {
        return repositoryStudent.getTotalStudentsCount();
    }

    @Override
    public int getAverageAgeStudent() {
        return repositoryStudent.getAverageStudentAge();
    }

    @Override
    public List<Student> getLastFiveStudent() {
        return repositoryStudent.findTop5Students();
    }

}
