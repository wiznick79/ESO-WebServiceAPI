package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.AvailabilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AvailabilityController.class)
public class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailabilityService availabilityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAvailabilityById() throws Exception{
        Explainer explainer = new Explainer("Nikolaos Perris");
        LocalDate d1 = LocalDate.of(1998,12,1);
        LocalTime t1 = LocalTime.of(14,1);
        LocalTime t2 = LocalTime.of(20,0);
        Availability availability = new Availability(explainer,t1,t2,d1);
        availability.setId(1L);

        when(this.availabilityService.findById(1L)).thenReturn(Optional.of(availability));

        String responseJson=this.mockMvc.perform(
                get("/availability/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        Availability responseAvailability = this.objectMapper.readValue(responseJson, Availability.class);

        this.objectMapper.readValue(responseJson,Appointment.class);
        assertEquals(availability, responseAvailability);


        this.mockMvc.perform(
                get("/availability/id=2")
        ).andExpect(
                status().isNotFound()
        );

    }
}
