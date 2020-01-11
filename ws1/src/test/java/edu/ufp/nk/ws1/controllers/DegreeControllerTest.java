package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.CollegeService;
import edu.ufp.nk.ws1.services.DegreeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DegreeController.class)
public class DegreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DegreeService degreeService;
    @MockBean
    private CollegeService collegeService;


    @Test
    void createDegreeByCollege() throws Exception{
       Degree degree = new Degree("Eng Infomartica");
        College college = new College("Ciencias");
        college.setId(2L);

       String jsonRequest = this.objectMapper.writeValueAsString(degree);

        when(degreeService.createDegreeByCollege(degree, 2L)).thenReturn(Optional.of(degree));

        this.mockMvc.perform(
                post("/degree/2").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );


        //Existing Degree
        Degree existingDegree = new Degree("Eng Infomartica");
        when(this.degreeService.createDegreeByCollege(existingDegree, 2L)).thenReturn(Optional.empty());
        String existingDegreeJson = this.objectMapper.writeValueAsString(existingDegree);
        this.mockMvc.perform(
                post("/degree/2").contentType(MediaType.APPLICATION_JSON).content(existingDegreeJson)
        ).andExpect(
                status().isNotFound()
                //TODO:................
        );

        //Non Existing College
        Degree nDegree = new Degree("Eng Infomartica 2");

        String jsonCollegeNorExistRequest = this.objectMapper.writeValueAsString(nDegree);

        when(degreeService.createDegreeByCollege(nDegree, 10L)).thenReturn(Optional.of(nDegree));

        this.mockMvc.perform(
                post("/degree/100000").contentType(MediaType.APPLICATION_JSON).content(jsonCollegeNorExistRequest)
        ).andExpect(
                status().isNotFound()
        );



    }

    @Test
    void getDegreeById() throws Exception {
        Degree degree = new Degree("Informatica");
        degree.setId(1L);

        when(this.degreeService.findById(1L)).thenReturn(Optional.of(degree));

        String responseJson=this.mockMvc.perform(
                get("/degree/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        Degree responseDegree = this.objectMapper.readValue(responseJson, Degree.class);
        assertEquals(degree, responseDegree);

        this.mockMvc.perform(
                get("/degree/id=2")
        ).andExpect(
                status().isNotFound()
        );
    }

    }
