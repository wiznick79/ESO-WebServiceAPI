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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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

        Student existingStudent = new Student("Nikos", 37000);

        when(this.studentService.createStudent(existingStudent)).thenReturn(Optional.empty());

        String existingStudentJson = this.objectMapper.writeValueAsString(existingStudent);

        this.mockMvc.perform(
                post("/student").contentType(MediaType.APPLICATION_JSON).content(existingStudentJson)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    public void getAllStudents() throws Exception {
        String responseJson=this.mockMvc.perform(
                get("/student")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();
    }

   @Test
    void getStudentById() throws Exception{
        Student student = new Student("asda", 123);
        student.setId(1L);

        when(this.studentService.findById(1L)).thenReturn(Optional.of(student));

        String responseJson=this.mockMvc.perform(
                get("/student/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


    Student responseStudent = this.objectMapper.readValue(responseJson, Student.class);
    assertEquals(student, responseStudent);

       this.mockMvc.perform(
               get("/student/id=2")
       ).andExpect(
               status().isNotFound()
       );

   }

    @Test
    void getStudentByNumber() throws Exception{
        Student student = new Student("asda", 123);
        student.setStudentNumber(123);

        when(this.studentService.findByNumber(123)).thenReturn(Optional.of(student));

        String responseJson=this.mockMvc.perform(
                get("/student/number=123")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        Student responseStudent = this.objectMapper.readValue(responseJson, Student.class);
        this.objectMapper.readValue(responseJson,Student.class);
        assertEquals(student, responseStudent);

        this.mockMvc.perform(
                get("/student/number=2")
        ).andExpect(
                status().isNotFound()
        );

    }


}
