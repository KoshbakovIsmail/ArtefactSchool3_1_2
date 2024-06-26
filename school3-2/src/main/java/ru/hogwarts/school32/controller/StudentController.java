package ru.hogwarts.school32.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "API для работы Студентами")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/printNameStudent")
    @Operation(summary = "Печать имени студетов по потоком")
    public void printStudent() {
        studentService.printStudentName();
    }

    @GetMapping("/printNameStudentSync")
    @Operation(summary = "Печать имени студетов по потоком sync")
    public void printStudentSync() {
        studentService.printStudentNameSync();
    }

    @GetMapping("/getStudentNameByA")
    @Operation(summary = "Получение имени Студента на букву А")
    public Collection<String> getNameByA() {
        return studentService.getNameByA();
    }

    @GetMapping("/getAverageAgeByStream")
    @Operation(summary = "Получение среднего возраста студентов с использованием Stream ")
    public Double getAverageAgeByStream() {
        return studentService.getAverageAgeByStream();
    }

    @GetMapping("/totalCount")
    @Operation(summary = "Получение общего количесто студентов")
    public int getTotalStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("/averageAge")
    @Operation(summary = "Получение среднего возраста в среди студентов")
    public int getAverageAgeStudents() {
        return studentService.getAverageAgeStudent();
    }

    @GetMapping("/lastFive")
    @Operation(summary = "Получение 5 последних студентов со списка")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudent();
    }

    @GetMapping
    @Operation(summary = "Получение всех значение")
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/faculty/{studentId}")
    @Operation(summary = "Получеть факультет студента по Id")
    public Faculty getFacultyById(@PathVariable Long studentId) {
        return studentService.getFaculty(studentId);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Получение студента по ID")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/age/{age}")
    @Operation(summary = "Фильтрация струдентов по возрасту")
    public List<Student> filterStudentAge(@PathVariable Integer age) {
        return studentService.filterStudentByAge(age);
    }

    @GetMapping("/age/{min}/{max}")
    @Operation(summary = "Получение струдентов в промежутке запроса")
    public List<Student> filterStudentByMinAndMaxAge(@PathVariable Integer min, @PathVariable Integer max) {
        return studentService.findByAgeBetweens(min, max);
    }

    @PostMapping()
    @Operation(summary = "Добавить студента")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение данных студента по ID")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента по ID")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);

    }
}
