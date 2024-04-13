package ru.hogwarts.school32.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.StudentNotFoundException;
import ru.hogwarts.school32.exception.StudentllegalArgumentException;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.service.StudentService;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private Map<Long, Student> studentMap = new HashMap<>();
    private Long idCount = 1L;

    @Override
    public Student createStudent(Student student) {
        Long id = idCount++;
        student.setId(id);
        studentMap.put(id, student);
        return student;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        if (studentMap.containsKey(id)) {
            student.setId(id);
            studentMap.put(id, student);
            return student;
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Student deleteStudent(Long id) {
        if (studentMap.containsKey(id)) {
            Student deleteStudent = studentMap.get(id);
            studentMap.remove(id);
            return deleteStudent;
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Student getStudentById(Long id) {
        if (studentMap.containsKey(id)) {
            Student result = studentMap.get(id);
            return result;
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Map<Long, Student> getAll() {
        return studentMap;
    }

    @Override
    public Map<Long, Student> filterStudentByAge(Integer age) {
        Map<Long, Student> filterStudent = new HashMap<>();
        for (Map.Entry<Long, Student> entry : studentMap.entrySet()) {
            Student student = entry.getValue();
            if (student.getAge() == age) {
                filterStudent.put(entry.getKey(), student);
            }
        }
        if (filterStudent.isEmpty()) {
            throw new StudentllegalArgumentException();
        }
        return filterStudent;
    }
}
