package ru.hogwarts.school32.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.StudentNotFoundException;
import ru.hogwarts.school32.exception.StudentllegalArgumentException;
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
        if (student == null) {
            throw new StudentllegalArgumentException();
        }
        if (repositoryStudent.existsById(id)) {
            student.setId(id);
            return repositoryStudent.save(student);
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Student deleteStudent(Long id) {
        if (repositoryStudent.existsById(id)) {
            Student deleteFacult = repositoryStudent.findById(id).orElse(null);
            repositoryStudent.deleteById(id);
            return deleteFacult;
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Student getStudentById(Long id) {
        return repositoryStudent.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public List<Student> getAll() {
        return repositoryStudent.findAll();
    }

    @Override
    public List<Student> filterStudentByAge(Integer age) {
        return repositoryStudent.findByAge(age);
    }
}
