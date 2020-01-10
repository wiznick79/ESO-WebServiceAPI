package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.LanguageService;
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

@WebMvcTest(controllers = LanguageController.class)
public class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LanguageService languageService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createLanguage() throws Exception{
        Language language = new Language("German");

        String jsonRequest=this.objectMapper.writeValueAsString(language);
        when(languageService.createLanguage(language)).thenReturn(Optional.of(language));


        this.mockMvc.perform(
                post("/language").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

        Language existingLanguage = new Language("German");

        when(this.languageService.createLanguage(existingLanguage)).thenReturn(Optional.empty());

        String existingStudentJson = this.objectMapper.writeValueAsString(existingLanguage);

        this.mockMvc.perform(
                post("/language").contentType(MediaType.APPLICATION_JSON).content(existingStudentJson)
        ).andExpect(
                status().isBadRequest()
        );

    }


    @Test
    void getLanguageById() throws Exception{
        Language language = new Language("German");
        language.setId(1L);

        when(this.languageService.findById(1l)).thenReturn(Optional.of(language));

        String responseJson=this.mockMvc.perform(
                get("/language/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        Language responseLanguage = this.objectMapper.readValue(responseJson, Language.class);
        assertEquals(language, responseLanguage);

        this.mockMvc.perform(
                get("/language/id=2")
        ).andExpect(
                status().isNotFound()
        );

    }

}
