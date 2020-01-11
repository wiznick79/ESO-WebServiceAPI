package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.services.AvailabilityService;
import edu.ufp.nk.ws1.services.LanguageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AvailabilityController.class)
public class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailabilityService availabilityService;

    @Autowired
    private ObjectMapper objectMapper;

    /*@Test
    void getAvailabilityById() throws Exception{
        Availability availability = new Availability()
    }*/
}
