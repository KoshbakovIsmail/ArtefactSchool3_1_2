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
import static org.springframework.http.HttpMethod.*;
import static ru.hogwarts.school32.controller.ModelConstantsTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerDbTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    FacultyServiceImpl facultyService;

    @Autowired
    StudentServiceImpl studentService;

    @Test
    void getAllTest() {
        createFacultyMock();
        List<Faculty> getFacultyList = restTemplate.exchange(
                "http://localhost:" + port + "/faculty", GET, null,
                new ParameterizedTypeReference<List<Faculty>>() {
                }).getBody();
        assertFalse(getFacultyList.isEmpty());
    }

    @Test
    void getFacultyByIdTest() {
        Faculty createFaculty = createFacultyMock();
        ResponseEntity<Faculty> getFaculty = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/id/" + createFaculty.getId(),
                Faculty.class);
        assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty newFaculty = getFaculty.getBody();
        assertThat(newFaculty.getName()).isEqualTo(createFaculty.getName());
        assertThat(newFaculty.getColor()).isEqualTo(createFaculty.getColor());
    }

    @Test
    void filterFacultyByColorTest() {
        createFacultyMock(FACULTY1);
        createFacultyMock(FACULTY2);
        List<Faculty> getFacultyList = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/color/{color}", GET, null,
                new ParameterizedTypeReference<List<Faculty>>() {
                }, FACULTY_COLOR_1).getBody();
        assertTrue(getFacultyList.size() >= 1);
    }

    @Test
    void getFacultyByColorOrNameTest() {
        createFacultyMock(FACULTY1);
        createFacultyMock(FACULTY2);
        List<Faculty> getFacultyList = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/get-by-color-or-name?color={color}&name={name}",
                GET, null, new ParameterizedTypeReference<List<Faculty>>() {
                },
                FACULTY_COLOR_1,
                FACULTY_NAME_1).getBody();
        assertTrue(getFacultyList.size() >= 1);
    }

    @Test
    void getStudentsFacultyByIdTest() {
        Faculty createFaculty = facultyService.createFaculty(FACULTY1);
        STUDENT1.setFaculty(createFaculty);
        Student createdStudent = studentService.createStudent(STUDENT1);
        ResponseEntity<List<Student>> getStudentsFaculty = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/student/{facultyId}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                },
                createFaculty.getId());
        assertThat(getStudentsFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Student> students = getStudentsFaculty.getBody();
        assertNotNull(students);
        assertThat(students.size()).isEqualTo(1);
        assertThat(students.get(0).getName()).isEqualTo(createdStudent.getName());
    }

    @Test
    void createFacultyTest() {
        ResponseEntity<Faculty> newFaculty = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                FACULTY1, Faculty.class);
        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty getFaculty = newFaculty.getBody();
        assertThat(getFaculty.getName()).isEqualTo(FACULTY1.getName());
        assertThat(getFaculty.getColor()).isEqualTo(FACULTY1.getColor());
    }

    @Test
    void updateFacultyTest() {
        Faculty createFaculty = createFacultyMock();
        createFaculty.setName(FACULTY_NAME_2);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Faculty> requestUpdate = new HttpEntity<>(createFaculty, headers);
        ResponseEntity<Faculty> updateFaculty = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + createFaculty.getId(),
                PUT,
                requestUpdate,
                Faculty.class);
        assertThat(updateFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<Faculty> getFaculty = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/id/" + createFaculty.getId(),
                Faculty.class);
        assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty updatedFaculty = getFaculty.getBody();
        assertThat(updatedFaculty.getName()).isEqualTo(createFaculty.getName());
    }

    @Test
    void deleteFacultyTest() {
        Faculty createFaculty = createFacultyMock();
        restTemplate.delete(
                "http://localhost:" + port + "/faculty/" + createFaculty.getId(),
                Faculty.class);
        ResponseEntity<Student> getFaculty = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/id/" + createFaculty.getId(),
                Student.class);
        assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    public Faculty createFacultyMock() {
        return facultyService.createFaculty(FACULTY1);
    }

    public Faculty createFacultyMock(Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }
}