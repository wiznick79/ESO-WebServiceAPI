package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Student;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AppointmentRepoTest {

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private ExplainerRepo explainerRepo;

    @Test
    @VisibleForTesting
    void test(){
        LocalDate d1 = LocalDate.of(1998,1,1);
        LocalTime t1 = LocalTime.now();
        Student student = new Student("Avaro", 37000);
        Explainer explainer = new Explainer("Pedro Alves");
        Appointment appointment = new Appointment(d1,t1,student,explainer);
        this.explainerRepo.save(explainer);
        this.studentRepo.save(student);
        this.appointmentRepo.save(appointment);
        assertEquals(1,appointmentRepo.count());
        Appointment appointment1 = new Appointment(d1,t1,student,explainer);
        this.appointmentRepo.save(appointment1);
        assertEquals(2,appointmentRepo.count());

    }
}
