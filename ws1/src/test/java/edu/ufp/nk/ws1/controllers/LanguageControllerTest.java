package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.LanguageService;
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

@WebMvcTest(controllers = LanguageController.class)
public class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LanguageService languageService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @VisibleForTesting
    void createLanguage() throws Exception{
        Language language = new Language("German");
        String jsonRequest=this.objectMapper.writeValueAsString(language);
        when(languageService.createLanguage(language)).thenReturn(Optional.of(language));


        String response = this.mockMvc.perform(
                post("/language").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        //Important
        Language responseLanguage = this.objectMapper.readValue(response, Language.class);
        assertEquals(responseLanguage, language);

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
    @VisibleForTesting
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

    @Test
    @VisibleForTesting
    void getAllLanguage() throws Exception {
        Set<Language> languages = new HashSet<>();
        languages.add(new Language("Portuguese"));
        languages.add(new Language("English"));
        languages.add(new Language("Greek"));

        when(this.languageService.findAll()).thenReturn(languages);

        String responseGetAllLanguages = this.mockMvc.perform(get("/language")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Set<Language> results = this.objectMapper.readValue(responseGetAllLanguages, new TypeReference<Set<Language>>() {
        });

        assertTrue(results.containsAll(languages));
    }


}
