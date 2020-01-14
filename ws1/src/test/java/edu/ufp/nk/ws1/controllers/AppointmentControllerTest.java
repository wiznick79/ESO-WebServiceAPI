package edu.ufp.nk.ws1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.services.AppointmentService;
import edu.ufp.nk.ws1.services.ExplainerService;
import edu.ufp.nk.ws1.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private ExplainerService explainerService;
    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void createAppointment() throws Exception{
        Student student = new Student("Alvaro", 37000);
        student.setId(1L);
        LocalDate d1 = LocalDate.now();
        LocalTime t1 = LocalTime.now();
        LocalTime t2 = LocalTime.of(23,1);
        Explainer explainer = new Explainer("Nikos Perris");
        Appointment appointment = new Appointment(d1,t1,student,explainer);



        String jsonRequest = this.objectMapper.writeValueAsString(appointment);
        System.out.println(jsonRequest);

        when(appointmentService.createAppointment(appointment)).thenReturn(Optional.of(appointment));


        this.mockMvc.perform(
                post("/appointment").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void getAppointmentById()throws Exception{
        Student student = new Student("Alvaro", 37000);
        student.setId(1L);
        LocalDate d1 = LocalDate.now();
        LocalTime t1 = LocalTime.now();
        Explainer explainer = new Explainer("Nikos Perris");
        Appointment appointment = new Appointment(d1,t1,student,explainer);
        appointment.setId(1L);

        when(this.appointmentService.findById(1L)).thenReturn(Optional.of(appointment));

        String responseJson=this.mockMvc.perform(
                get("/appointment/id=1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        Appointment responseAppointment = this.objectMapper.readValue(responseJson, Appointment.class);

        this.objectMapper.readValue(responseJson,Appointment.class);
        assertEquals(appointment, responseAppointment);

        this.mockMvc.perform(
                get("/appointment/id=2")
        ).andExpect(
                status().isNotFound()
        );
    }
}
