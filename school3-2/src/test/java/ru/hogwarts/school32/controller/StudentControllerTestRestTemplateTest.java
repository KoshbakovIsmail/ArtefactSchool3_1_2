package ru.hogwarts.school32.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school32.model.Student;

import static ru.hogwarts.school32.controller.ModelConstantsTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void getAllTest() {
        /*Student[] students = restTemplate.getForObject("http:localhost:" + port + "/student", Student[].class);
        Assertions.assertThat(students).isNotNull();*/
    }

    @Test
    public void getFacultyByIdTest() {
       /* Long studentId = 1L;
        String url = "http://localhost:" + port + "/student/faculty/" + studentId;
        String facultyName = restTemplate.getForObject(url, String.class);
        Assertions.assertThat(facultyName).isNotNull();*/
    }

    @Test
    public void getStudentByIdTest() {
      /*  Long studentId = 1L;
        Student student = restTemplate.getForObject("http://localhost:" + port
                + "/student/id" + studentId, Student.class);
        Assertions.assertThat(student).isNotNull();*/
    }

    @Test
    public void filterStudentAgeTest() {
       /* int age = 20;
        String url = "http://localhost:" + port + "/student/age/" + age;
        List<Student> expStudent = new ArrayList<>();
        expStudent.add(new Student(1L, "Jonni", 20));
        expStudent.add(new Student(2L, "Sara", 20));

        ResponseEntity<List<Student>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> filteredStudents = response.getBody();
        Assertions.assertThat(filteredStudents).isNotNull();
        Assertions.assertThat(filteredStudents.toArray(new Student[0])).containsExactly(expStudent.toArray(new Student[0]));*/
    }

    @Test
    public void filterStudentByMinAndMaxAgeTest() {
       /* int minAge = 12;
        int maxAge = 20;
        String url = "http://localhost:" + port + "/student/age/" + minAge + maxAge;
        List<Student> expStudent = new ArrayList<>();
        expStudent.add(new Student(1L, "Jonni", 13));
        expStudent.add(new Student(2L, "Sara", 18));
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> filteredStudents = response.getBody();
        List<Student> filter = filteredStudents.stream().sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
        List<Student> expFilter = expStudent.stream().toList().stream().sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());

        Assertions.assertThat(filteredStudents).isNotNull();
        Assertions.assertThat(filteredStudents.toArray(new Student[1]))
        .containsExactly(expStudent.toArray(new Student[1]));
        */
    }

    @Test
    public void createStudentTest() {
        STUDENT2.setFaculty(FACULTY2);
ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/student",
                STUDENT2,
                Student.class);
        Student createNewStudent = response.getBody();
        Assertions.assertThat(createNewStudent).isNotNull();
        Assertions.assertThat(createNewStudent).isEqualTo(STUDENT2);
    }

    @Test
    public void updateStudentTest() {
       Long studentId = 8L;
        Student updatedStudent = new Student(studentId, "Updated Name", 25);

        restTemplate.put("http://localhost:" + port + "/student/" + studentId, updatedStudent);

        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:" + port + "/student/" + studentId, Student.class);
        Student retrievedStudent = response.getBody();

        Assertions.assertThat(retrievedStudent).isNotNull();
        Assertions.assertThat(retrievedStudent).isEqualTo(updatedStudent);
    }

    @Test
    public void deleteStudentTest() {

    }
}