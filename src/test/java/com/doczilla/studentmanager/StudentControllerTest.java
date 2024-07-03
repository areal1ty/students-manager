package com.doczilla.studentmanager;

import com.doczilla.studentmanager.controller.StudentController;
import com.doczilla.studentmanager.model.Student;
import com.doczilla.studentmanager.controller.config.WebAppConfig;
import com.doczilla.studentmanager.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {WebAppConfig.class, StudentController.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;

    @Test
    void testGetAllStudents() throws Exception {
        List.of(
                new Student(null, "John", "Smith", "Doe", LocalDate.of(2000, 3, 1), "CS123"),
                new Student(null, "John", "Smith", "Doe", LocalDate.of(2005, 3, 1), "CS03"),
                new Student(null, "John", "Smith", "Doe", LocalDate.of(2001, 5, 1), "CS13"),
                new Student(null, "John", "Smith", "Doe", LocalDate.of(2002, 3, 1), "CS103"),
                new Student(null, "John", "Smith", "Doe", LocalDate.of(2005, 3, 1), "CS103"),
                new Student(null, "John", "Smith", "Doe", LocalDate.of(2000, 1, 1), "CS103"),
                new Student(null, "John", "Smith", "Doe", LocalDate.of(2008, 3, 1), "CS1a3")
        ).forEach(studentService::save);

        mockMvc.perform(
                        get("/students")
                ).andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student(null, "Jane", "Doe", "Doe", LocalDate.of(2002, 12, 2), "CS102");
        Student savedStudent = studentService.save(student);
        when(savedStudent).thenReturn(savedStudent);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteStudent() throws Exception {
        Student student = new Student(null, "John", "Smith", "Doe", LocalDate.of(2000, 3, 1), "CS103");
        studentService.save(student);
        mockMvc.perform(delete("/students/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
