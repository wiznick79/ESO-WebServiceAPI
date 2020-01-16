package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.services.CollegeService;
import edu.ufp.nk.ws1.services.CourseService;
import edu.ufp.nk.ws1.services.DegreeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;
    @MockBean
    private DegreeService degreeService;
    @MockBean
    private CollegeService collegeService;

    @Test
    void createCourseByDegree() throws Exception {
        Course course = new Course("Multimedia");
        College college = new College("Ciencias");
        Degree degree = new Degree("Eng Informatica");
        degree.setId(1L);
        college.setId(1L);
        when(degreeService.createDegreeByCollege(degree,1L)).thenReturn(Optional.of(degree));


        String jsonRequest = this.objectMapper.writeValueAsString(course);
        when(courseService.createCourseByDegree(course, 1L)).thenReturn(Optional.of(course));
        String response = this.mockMvc.perform(
                post("/course/1").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        Course responseCourse = this.objectMapper.readValue(response, Course.class);
        assertEquals(responseCourse, course);

       /*//Existing Course
        Course course1 = new Course("Multimedia");
        String jsonExistingCourse = this.objectMapper.writeValueAsString(course1);
        when(courseService.createCourseByDegree(course1, 1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(
                post("/course/1").contentType(MediaType.APPLICATION_JSON).content(jsonExistingCourse)
        ).andExpect(
                status().isNotFound()
        );*/
                //TODO:................

        //Non Existing Degree
        Course courseN = new Course("Multimedia");
        Degree degreeN = new Degree("Eng Informatica");
        degreeN.setId(1L);

        String jsonDegreeNotExistRequest = this.objectMapper.writeValueAsString(courseN);
        when(courseService.createCourseByDegree(courseN, 1L)).thenReturn(Optional.of(course));

        this.mockMvc.perform(
                post("/course/10").contentType(MediaType.APPLICATION_JSON).content(jsonDegreeNotExistRequest)
        ).andExpect(
                status().isNotFound()
        );
    }

    @Test
    void getAllCourses() throws Exception{
        Set<Course> courses = new HashSet<>();
        courses.add(new Course("Base de Dados"));
        courses.add(new Course("Engenharia de Software"));
        courses.add(new Course("Multimedia"));

        when(this.courseService.findAll()).thenReturn(courses);

        String responseGetAllCourses = this.mockMvc.perform(get("/course")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Set<Course> results=this.objectMapper.readValue(responseGetAllCourses,new TypeReference<Set<Course>>(){});

        assertTrue(results.containsAll(courses));
    }

    @Test
    void getCourseById() throws Exception {
        Course course = new Course("Multimedia");
        Degree degree = new Degree("Eng Informatica");
        course.setId(1L);
        course.setDegree(degree);

        when(this.courseService.findById(1L)).thenReturn(Optional.of(course));

        String responseJson = this.mockMvc.perform(
                get("/course/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        Course responseCourse = this.objectMapper.readValue(responseJson, Course.class);
        this.objectMapper.readValue(responseJson, Course.class);
        assertEquals(course, responseCourse);

        this.mockMvc.perform(
                get("/course/id=2")
        ).andExpect(
                status().isNotFound()
        );
    }
}
