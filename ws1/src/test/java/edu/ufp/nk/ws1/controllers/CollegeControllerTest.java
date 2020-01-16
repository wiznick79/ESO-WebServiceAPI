package edu.ufp.nk.ws1.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.services.CollegeService;
import org.assertj.core.util.VisibleForTesting;
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

@WebMvcTest(controllers = CollegeController.class)
public class CollegeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollegeService collegeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCollege() throws Exception{
        College college = new College("UFP");

        String jsonRequest=this.objectMapper.writeValueAsString(college);

        when(collegeService.createCollege(college)).thenReturn(Optional.of(college));

        String responseJson=this.mockMvc.perform(
                post("/college").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        College responseCollege = this.objectMapper.readValue(responseJson, College.class);
        assertEquals(college, responseCollege);

        College existingCollege = new College("UFP");

        when(this.collegeService.createCollege(existingCollege)).thenReturn(Optional.empty());

        String existingStudentJson = this.objectMapper.writeValueAsString(existingCollege);

        this.mockMvc.perform(
                post("/college").contentType(MediaType.APPLICATION_JSON).content(existingStudentJson)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    void getAllColleges() throws Exception{
        Set<College> colleges = new HashSet<>();
        colleges.add(new College("Saude"));
        colleges.add(new College("Ciencas"));
        colleges.add(new College("humanas"));

        when(this.collegeService.findAll()).thenReturn(colleges);

        String responseGetAllCollege = this.mockMvc.perform(get("/college")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Set<College> results=this.objectMapper.readValue(responseGetAllCollege,new TypeReference<Set<College>>(){});

        assertTrue(results.containsAll(colleges));
    }

    @Test
    @VisibleForTesting
    void getCollegeById() throws Exception{
        College college = new College("UFP");
        college.setId(1L);

        when(this.collegeService.findById(1l)).thenReturn(Optional.of(college));

        String responseJson=this.mockMvc.perform(
                get("/college/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        College responseCollege = this.objectMapper.readValue(responseJson, College.class);
        assertEquals(college, responseCollege);

        this.mockMvc.perform(
                get("/student/id=2")
        ).andExpect(
                status().isNotFound()
        );
    }
}
