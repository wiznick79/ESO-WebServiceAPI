package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.repositories.AppointmentRepo;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import edu.ufp.nk.ws1.repositories.StudentRepo;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AppointmentService.class)
@VisibleForTesting
class AppointmentServiceTest{
    @Autowired
    private AppointmentService appointmentService;
    @MockBean
    private AppointmentRepo appointmentRepo;
    @MockBean
    private ExplainerRepo explainerRepo;
    @MockBean
    private StudentRepo studentRepo;

    @Test
    @VisibleForTesting
    void accessAppointment(){
        LocalDate d1 = LocalDate.of(1998, 1, 1);
        LocalTime t1 = LocalTime.now();
        Student student = new Student("Avaro", 37000);
        Explainer explainer = new Explainer("Pedro Alves");
        Appointment appointment = new Appointment(d1, t1, student, explainer);
        Appointment appointment1 = new Appointment(d1, t1, student, explainer);
        this.appointmentRepo.save(appointment);
        this.appointmentService.createAppointment(appointment);
        this.explainerRepo.save(explainer);
        this.studentRepo.save(student);

        when(this.appointmentRepo.findById(1L)).thenReturn(Optional.of(appointment));
        assertNotNull(appointment);

        Set<Appointment> appointments = new HashSet<>();
        appointments.add(appointment);
        appointments.add(appointment1);

        when(this.appointmentService.findAll()).thenReturn(appointments);
        assertNotNull(appointments);

        when(this.appointmentService.findById(1L)).thenReturn(Optional.of(appointment));
        assertNotNull(appointment);

        when(this.appointmentService.findByDate(d1)).thenReturn(Optional.of(appointment));
        assertNotNull(appointment);
        when(this.appointmentService.findByStartAndDate(t1,d1)).thenReturn(Optional.of(appointment));
        assertNotNull(appointment);


    }
}