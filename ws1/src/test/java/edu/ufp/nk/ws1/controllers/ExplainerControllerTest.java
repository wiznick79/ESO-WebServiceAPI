package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.CollegeService;
import edu.ufp.nk.ws1.services.DegreeService;
import edu.ufp.nk.ws1.services.ExplainerService;
import edu.ufp.nk.ws1.services.LanguageService;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;
import java.util.Optional;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    @MockBean
    private DegreeService degreeService;
    @MockBean
    private CollegeService collegeService;
    @MockBean
    private LanguageService languageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @VisibleForTesting
    void createExplainer() throws Exception {
        Explainer explainer = new Explainer("Nikos Perris");

        String jsonRequest = this.objectMapper.writeValueAsString(explainer);
        when(explainerService.createExplainer(explainer)).thenReturn(Optional.of(explainer));


        String response = this.mockMvc.perform(
                post("/explainer").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        Explainer responseExplainer = this.objectMapper.readValue(response, Explainer.class);
        assertEquals(responseExplainer, explainer);

        Explainer existingExpainer = new Explainer("Nikos Perris");

        when(this.explainerService.createExplainer(existingExpainer)).thenReturn(Optional.empty());

        String existingStudentJson = this.objectMapper.writeValueAsString(existingExpainer);

        this.mockMvc.perform(
                post("/explainer").contentType(MediaType.APPLICATION_JSON).content(existingStudentJson)
        ).andExpect(
                status().isBadRequest()
        );


    }

    @Test
    @VisibleForTesting
    void getAllExplainers() throws Exception {
        Set<Explainer> explainers = new HashSet<>();
        explainers.add(new Explainer("Nikos Perris"));
        explainers.add(new Explainer("Pedro Alves"));
        explainers.add(new Explainer("Alvaro Magalhaes"));

        when(this.explainerService.findAll()).thenReturn(explainers);

        String responseGetAllExplainers = this.mockMvc.perform(get("/explainer")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Set<Explainer> results = this.objectMapper.readValue(responseGetAllExplainers, new TypeReference<Set<Explainer>>() {
        });

        assertTrue(results.containsAll(explainers));
    }

    @Test
    @VisibleForTesting
    void getExplainerById() throws Exception {
        Explainer explainer = new Explainer("Nikos Perris");
        Degree degree = new Degree("Informatica");
        College college = new College("Ciencias");
        college.setId(1L);
        degreeService.createDegreeByCollege(degree, 1L);
        explainer.setId(1L);
        explainer.setDegree(degree);

        when(this.explainerService.findById(1L)).thenReturn(Optional.of(explainer));

        String responseJson = this.mockMvc.perform(
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
    @VisibleForTesting
    void getExplainerByName() throws Exception {
        Explainer explainer = new Explainer("Nikos Perris");

        when(this.explainerService.findByName("Nikos Perris")).thenReturn(Optional.of(explainer));

        String responseJson = this.mockMvc.perform(
                get("/explainer/Nikos Perris")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        Explainer responseExplainer = this.objectMapper.readValue(responseJson, Explainer.class);
        assertEquals(explainer, responseExplainer);

        this.mockMvc.perform(
                get("/explainer/2")
        ).andExpect(
                status().isNotFound()
        );

    }

    @Test
    @VisibleForTesting
    void updateExplainer() throws Exception {
        Explainer explainer = new Explainer("Nikos Perris");
        Degree degree = new Degree("Enfermagem");
        College college = new College("Saude");
        when(this.collegeService.createCollege(college)).thenReturn(Optional.of(college));
        when(this.degreeService.createDegreeByCollege(degree,college.getId())).thenReturn(Optional.of(degree));
        when(this.explainerService.findByName("Nikos Perris")).thenReturn(Optional.of(explainer));

        String result = this.mockMvc.perform(MockMvcRequestBuilders
                .put("/explainer/Enfermagem")
                .content("{\"name\":\"Nikos Perris\"}").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.degree").value("Enfermagem")).toString();
        //TODO: see how to perform a put
        assertEquals(result, explainer);
    }

    @Test
    @VisibleForTesting
    void setExplainerService() throws Exception{
        Explainer explainer = new Explainer("Nikos Perris");
        Explainer explainer1 = new Explainer("Pedro Alves");
        Set<Explainer> explainers = new HashSet<>();
        explainers.add(explainer);
        explainers.add(explainer1);
        Map<String, String> query = new HashMap<>();
        when(this.explainerService.filterExplainers(query)).thenReturn(explainers);
        assertNotNull(explainers);
    }

    @Test
    @VisibleForTesting
    void updateExplainerForAvailability() throws Exception {
        Explainer explainer = new Explainer("Nikos Perris");
        Degree degree = new Degree("Enfermagem");
        College college = new College("Saude");
        when(this.collegeService.createCollege(college)).thenReturn(Optional.of(college));
        when(this.degreeService.createDegreeByCollege(degree,college.getId())).thenReturn(Optional.of(degree));
        when(this.explainerService.findByName("Nikos Perris")).thenReturn(Optional.of(explainer));

        String result = this.mockMvc.perform(MockMvcRequestBuilders
                .put("/explainer/")
                .content("{\"explainer\" : {\"name\" : \"Nikos Perris\"},\"start\" : \"15:30\",\"end\" : \"17:30\",\"dayOfWeek\" : \"3\" }").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.degree").value("Enfermagem")).toString();
        //TODO: see how to perform a put
        assertEquals(result, explainer);
    }



    @Test
    @VisibleForTesting
    void languageExplainer() throws Exception {
        Explainer explainer = new Explainer("Nikos Perris");
        Language language = new Language("Portugues");
        String jsonRequest = this.objectMapper.writeValueAsString("name: " + explainer.getName());
        when(explainerService.createExplainer(explainer)).thenReturn(Optional.of(explainer));
        when(languageService.createLanguage(language)).thenReturn(Optional.of(language));

        System.out.println(jsonRequest);
        String response = this.mockMvc.perform(
                post("/explainer/language=Portugues")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        Explainer responseExplainer = this.objectMapper.readValue(response, Explainer.class);
        assertEquals(responseExplainer, explainer);



    }


}
