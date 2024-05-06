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
import ru.hogwarts.school32.Impl.FacultyServiceImpl;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.repositorys.RepositoryFaculty;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school32.controller.ModelConstantsTest.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryFaculty repositoryFaculty;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private ObjectMapper mapper = new ObjectMapper();
    @Test
    void getAllTest() throws Exception{
        when(repositoryFaculty.findAll()).thenReturn(FACULTY_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(FACULTY_LIST)));
    }

    @Test
    void getFacultyByIdTest() throws Exception{
        when(repositoryFaculty.findById(any(Long.class))).thenReturn(Optional.of(FACULTY1));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/id/" + FACULTY_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(FACULTY_NAME_1))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR_1));
    }

    @Test
    void filterFacultyByColorTest()  throws Exception{
        when(repositoryFaculty.findByColor(
                (anyString()))).thenReturn(FACULTY_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color/" + FACULTY_COLOR_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(FACULTY_LIST)));
    }

    @Test
    void getFacultyByColorOrNameTest() throws Exception{
        when(repositoryFaculty.findByColorIgnoreCaseAndNameIgnoreCase
                (anyString(), anyString())).thenReturn(FACULTY_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/get-by-color-or-name?color=" + FACULTY_COLOR_1
                        +"&name=" + FACULTY_NAME_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(FACULTY_LIST)));
    }

    @Test
    void getStudentsFacultyByIdTest() throws Exception{
        FACULTY1.setStudentList(STUDENT_LIST);

        when(repositoryFaculty.findById(any(Long.class))).thenReturn(Optional.of(FACULTY1));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/student/" + FACULTY_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(STUDENT_LIST)));
    }

    @Test
    void createFacultyTest() throws Exception{
        when(repositoryFaculty.save(any(Faculty.class))).thenReturn(FACULTY1);

        JSONObject createFacultyR = new JSONObject();
        createFacultyR.put("name", FACULTY_NAME_1);
        createFacultyR.put("color", FACULTY_COLOR_1);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                                .content(createFacultyR.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(FACULTY_NAME_1))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR_1));
    }

    @Test
    void updateFacultyTest() throws Exception{
        when(repositoryFaculty.findById(any(Long.class))).thenReturn(Optional.of(FACULTY1));
        when(repositoryFaculty.save(any(Faculty.class))).thenReturn(FACULTY1);

        JSONObject updateFaculty = new JSONObject();
        updateFaculty.put("id", FACULTY_ID_1);
        updateFaculty.put("name", FACULTY_NAME_1);
        updateFaculty.put("color", FACULTY_COLOR_1);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/" + FACULTY_ID_1)
                        .content(updateFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(FACULTY_NAME_1))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR_1));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        when(repositoryFaculty.save(any(Faculty.class))).thenReturn(FACULTY1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + FACULTY_ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}