package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createStudent() throws Exception{
        Student student = new Student("Alvaro Magalhães", 37000);

        String jsonRequest=this.objectMapper.writeValueAsString(student);

        when(studentService.createStudent(student)).thenReturn(Optional.of(student));

        this.mockMvc.perform(
                post("/student").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    public void getAllStudents(){

    }
/*
    @Test
    void getStudentById() throws Exception{
        Student student = new Student("asda", 123);
        student.setId(1L);

        when(this.studentService.findById(1l)).thenReturn(Optional.of(student));

        String responseJson=this.mockMvc.perform(
                get("/client/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();
    }

    Student responseStudent = this.objectMapper.readValue(responseJson, Student.class);
    assertEquals(student, responseStudent);

    this.mockMvc.perform(
    get("/client/id=2")
        ).andExpect(
            status().isNotFound()
        );
*/
}
