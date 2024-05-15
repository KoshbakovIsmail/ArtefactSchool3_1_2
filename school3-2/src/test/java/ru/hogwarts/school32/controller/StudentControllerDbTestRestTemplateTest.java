package ru.hogwarts.school32.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school32.Impl.FacultyServiceImpl;
import ru.hogwarts.school32.Impl.StudentServiceImpl;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school32.controller.ModelConstantsTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerDbTestRestTemplateTest {

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
        createStudentMockTest();

        List<Student> getStudentList = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }).getBody();
        assertFalse(getStudentList.isEmpty());
    }

    @Test
    void getFacultyByIdTest() {
        Faculty createFaculty = facultyService.createFaculty(FACULTY1);
        STUDENT1.setFaculty(createFaculty);
        Student createdStudent = studentService.createStudent(STUDENT1);

        ResponseEntity<Faculty> getFacultyStudent = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/faculty/" + createdStudent.getId(),
                Faculty.class
        );
        assertThat(getFacultyStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(getFacultyStudent.getBody());
        assertThat(getFacultyStudent.getBody().getName()).isEqualTo(createdStudent.getFaculty().getName());
    }

    @Test
    void getStudentByIdTest() {
        Student createStudent = createStudentMockTest();
        ResponseEntity<Student> getStudent = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/id/" + createStudent.getId(),
                Student.class);

        assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudent = getStudent.getBody();
        assertThat(newStudent.getName()).isEqualTo(createStudent.getName());
        assertThat(newStudent.getAge()).isEqualTo(createStudent.getAge());
    }

    @Test
    void filterStudentAgeTest() {
        createStudentMockTest2(STUDENT1);
        createStudentMockTest2(STUDENT2);
        List<Student> getStudentList = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/{age}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }, (STUDENT_AGE_1)).getBody();

        assertTrue(getStudentList.size() >= 1);
    }

    @Test
    void filterStudentByMinAndMaxAgeTest() {
        createStudentMockTest2(STUDENT1);
        createStudentMockTest2(STUDENT2);
        List<Student> getStudentList = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/{min}/{max}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                },
                (STUDENT_AGE_1 - 2),
                (STUDENT_AGE_2 + 2)).getBody();

        assertTrue(getStudentList.size() >= 2);
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
        Student createStudent = createStudentMockTest();
        createStudent.setName(STUDENT_NAME_2);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Student> requestUpdate = new HttpEntity<>(createStudent, headers);
        ResponseEntity<Student> updateResponse = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + createStudent.getId(),
                HttpMethod.PUT,
                requestUpdate,
                Student.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<Student> getStudent = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/id/" + createStudent.getId(),
                Student.class);
        assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student updatedStudent = getStudent.getBody();
        assertThat(updatedStudent.getName()).isEqualTo(createStudent.getName());
    }

    @Test
    void deleteStudentTest() {
        Student createStudent = createStudentMockTest();

        restTemplate.delete(
                "http://localhost:" + port + "/student/" + createStudent.getId(),
                Student.class);

        ResponseEntity<Student> getStudent = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/id/" + createStudent.getId(),
                Student.class);

        assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    public Student createStudentMockTest() {
        return studentService.createStudent(STUDENT1);
    }

    public Student createStudentMockTest2(Student student) {
        return studentService.createStudent(student);
    }
}