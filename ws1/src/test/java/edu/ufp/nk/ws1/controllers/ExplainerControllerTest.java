package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.ExplainerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExplainerController.class)
public class ExplainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExplainerService explainerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createExplainer() throws Exception{
        Explainer explainer = new Explainer("Nikos Perris");

        String jsonRequest=this.objectMapper.writeValueAsString(explainer);
        when(explainerService.createExplainer(explainer)).thenReturn(Optional.of(explainer));


        this.mockMvc.perform(
                post("/explainer").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    void getAllExplainers(){

    }

    @Test
    void getExplainerById() throws Exception{
        Explainer explainer = new Explainer("Nikos Perris");
        explainer.setId(1L);

        when(this.explainerService.findById(1L)).thenReturn(Optional.of(explainer));

        String responseJson=this.mockMvc.perform(
                get("/explainer/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        Explainer responseExplainer = this.objectMapper.readValue(responseJson, Explainer.class);
        assertEquals(explainer, responseExplainer);

        this.mockMvc.perform(
                get("/explainer/id=2")
        ).andExpect(
                status().isNotFound()
        );
    }

    @Test
    void getExplainerByName() throws Exception{
        Explainer explainer = new Explainer("Nikos Perris");

        when(this.explainerService.findByName("Nikos Perris")).thenReturn(Optional.of(explainer));

        String responseJson=this.mockMvc.perform(
                get("/explainer/name=Nikos Perris")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        Explainer responseExplainer = this.objectMapper.readValue(responseJson, Explainer.class);
        assertEquals(explainer, responseExplainer);

        this.mockMvc.perform(
                get("/explainer/name = 2")
        ).andExpect(
                status().isNotFound()
        );
    }
}