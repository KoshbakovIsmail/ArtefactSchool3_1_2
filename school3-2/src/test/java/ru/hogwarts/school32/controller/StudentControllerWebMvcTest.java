package ru.hogwarts.school32.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school32.Impl.StudentServiceImpl;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.repositorys.RepositoryStudent;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school32.controller.ModelConstantsTest.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryStudent repositoryStudent;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllTest() throws Exception{
        when(repositoryStudent.findAll()).thenReturn(STUDENT_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get("/student" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(STUDENT_LIST)));
    }

    @Test
    void getFacultyByIdTest() throws Exception{
        when(repositoryStudent.findById(any(Long.class))).thenReturn(Optional.of(STUDENT1));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/id/" + STUDENT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(STUDENT_NAME_1))
                .andExpect(jsonPath("$.age").value(STUDENT_AGE_1));
    }
    @Test
    void getStudentByIdTest() throws Exception{
        when(repositoryStudent.findById(anyLong())).thenReturn(Optional.of(STUDENT1));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/id/" + STUDENT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(STUDENT1)));
    }

    @Test
    void filterStudentAgeTest() throws Exception{
        when(repositoryStudent.findByAge
                (anyInt())).thenReturn(STUDENT_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/age/" + STUDENT_AGE_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(STUDENT_LIST)));
    }

    @Test
    public void filterStudentByMinAndMaxAgeTest() throws Exception {
        when(repositoryStudent.findByAgeBetween
                (anyInt(), anyInt())).thenReturn(STUDENT_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/age/" + STUDENT_AGE_1
                                        +"/" + STUDENT_AGE_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(STUDENT_LIST)));
    }

    @Test
    void createStudentTest() throws Exception {
        when(repositoryStudent.save(any(Student.class))).thenReturn(STUDENT1);

        JSONObject createStudentR = new JSONObject();
        createStudentR.put("name", STUDENT_NAME_1);
        createStudentR.put("age", STUDENT_AGE_1);
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(createStudentR.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(STUDENT_NAME_1))
                .andExpect(jsonPath("$.age").value(STUDENT_AGE_1));
    }

    @Test
    void updateStudentTest() throws Exception {
        when(repositoryStudent.findById(any(Long.class))).thenReturn(Optional.of(STUDENT1));
        when(repositoryStudent.save(any(Student.class))).thenReturn(STUDENT1);

        JSONObject updateStudent = new JSONObject();
        updateStudent.put("id", STUDENT_ID_1);
        updateStudent.put("name", STUDENT_NAME_1);
        updateStudent.put("age", STUDENT_AGE_1);

        mockMvc.perform(MockMvcRequestBuilders.put("/student/" + STUDENT_ID_1)
                        .content(updateStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(STUDENT_NAME_1))
                .andExpect(jsonPath("$.age").value(STUDENT_AGE_1));
    }

    @Test
    void deleteStudentTest() throws Exception {
        when(repositoryStudent.save(any(Student.class))).thenReturn(STUDENT1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/" + STUDENT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}