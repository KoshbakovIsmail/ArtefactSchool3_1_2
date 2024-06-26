package ru.hogwarts.school32.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.StudentNotFoundException;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.repositorys.RepositoryStudent;
import ru.hogwarts.school32.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final RepositoryStudent repositoryStudent;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(RepositoryStudent repositoryStudent) {
        this.repositoryStudent = repositoryStudent;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Вызвано метод createStudent у класса StudentServiceImpl");
        return repositoryStudent.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        logger.info("Вызвано метод updateStudent у класса StudentServiceImpl ");
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
        logger.info("Вызвано метод deleteStudent у класса StudentServiceImpl");
        Student deletedStudent = repositoryStudent.findById(id).orElseThrow(() -> new StudentNotFoundException());
        repositoryStudent.deleteById(id);
        return deletedStudent;
    }

    @Override
    public Student getStudentById(Long id) {
        logger.info("Вызвано метод getStudentById у класса StudentServiceImpl");
        return repositoryStudent.findById(id).orElseThrow(() -> new StudentNotFoundException());
    }

    @Override
    public List<Student> getAll() {
        logger.info("Вызвано метод getAll у класса StudentServiceImpl");
        return repositoryStudent.findAll();
    }

    @Override
    public List<Student> filterStudentByAge(Integer age) {
        logger.info("Вызвано метод filetStudentByAge у класса StudentServiceImpl");
        return repositoryStudent.findByAge(age);
    }

    @Override
    public List<Student> findByAgeBetweens(Integer min, Integer max) {
        logger.info("Вызвано метод findByAgeBetweens у класса StudentServiceImpl");
        return repositoryStudent.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFaculty(Long studentId) {
        logger.info("Вызвано метод getFaculty у класса StudentServiceImpl");
        Student student = getStudentById(studentId);
        return student.getFaculty();
    }

    @Override
    public int getStudentCount() {
        logger.info("Вызвано метод getStudentCount у класса StudentServiceImpl");
        return repositoryStudent.getTotalStudentsCount();
    }

    @Override
    public int getAverageAgeStudent() {
        logger.info("Вызвано метод getAverageAgeStudent у класса StudentServiceImpl");
        return repositoryStudent.getAverageStudentAge();
    }

    @Override
    public List<Student> getLastFiveStudent() {
        logger.info("Вызвано метод getLastFiveStudent у класса StudentServiceImpl");
        return repositoryStudent.findTop5Students();
    }

    @Override
    public Collection<String> getNameByA() {
        logger.info("Вызвано метод getNameByA у класса StudentServiceImpl");
        return repositoryStudent.findAll().stream()
                .filter(s -> s.getName().startsWith("A"))
                .map(n -> n.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeByStream() {
        logger.info("Вызвано метод getAverageAgeByStream у класса StudentServiceImpl");
        return repositoryStudent.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0f);
    }

    @Override
    public void printStudentName() {
        List<Student> students = repositoryStudent.findAll();
        if (students.size() >= 6) {
            students.subList(0, 2).forEach(this::printStudentName1);
            printStudentNameStart(students.subList(2, 4));
            printStudentNameStart(students.subList(4, 6));
        }
    }

    private void printStudentName1(Student student) {
        logger.info("Student, id: {}, name: {}", student.getId(), student.getName());
    }

    private void printStudentNameStart(List<Student> student) {
        new Thread(() -> {
            student.forEach(this::printStudentName1);
        }).start();
    }

    @Override
    public void printStudentNameSync() {
        List<Student> students = repositoryStudent.findAll();
        if (students.size() >= 6) {
            students.subList(0, 2).forEach(this::printStudentName1Sync);
            printStudentNameStartSync(students.subList(2, 4));
            printStudentNameStartSync(students.subList(4, 6));
        }
    }

    private synchronized void printStudentName1Sync(Student student) {
        logger.info("Student, id: {}, name: {}", student.getId(), student.getName());
    }

    private void printStudentNameStartSync(List<Student> student) {
        new Thread(() -> {
            student.forEach(this::printStudentName1Sync);
        }).start();
    }
}
