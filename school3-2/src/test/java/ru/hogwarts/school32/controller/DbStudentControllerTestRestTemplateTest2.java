package ru.hogwarts.school32.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school32.Impl.FacultyServiceImpl;
import ru.hogwarts.school32.Impl.StudentServiceImpl;
import ru.hogwarts.school32.model.Student;

import static org.assertj.core.api.Assertions.*;
import static ru.hogwarts.school32.controller.ModelConstantsTest.STUDENT1;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DbStudentControllerTestRestTemplateTest2 {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private FacultyServiceImpl facultyService;


    @Test
    void getAllTest() {
    }

    @Test
    void getFacultyByIdTest() {
    }

    @Test
    void getStudentByIdTest() {
    }

    @Test
    void filterStudentAgeTest() {
    }

    @Test
    void filterStudentByMinAndMaxAgeTest() {
    }

    @Test
    void createStudentTest() {
        ResponseEntity<Student> newStudent = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                STUDENT1, Student.class);
        assertThat(newStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student getStudent = newStudent.getBody();
        assertThat(getStudent.getName()).isEqualTo(STUDENT1.getName());
        assertThat(getStudent.getAge()).isEqualTo(STUDENT1.getAge());
    }

    @Test
    void updateStudentTest() {
    }

    @Test
    void deleteStudentTest() {
    }
}