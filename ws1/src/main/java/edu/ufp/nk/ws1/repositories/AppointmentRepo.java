package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppointmentRepo extends CrudRepository<Appointment, Long> {
    Optional<Appointment> findById(long id);
    Optional<Appointment> findByDate(LocalDate date);
    Optional<Appointment> findByStartAndDate(LocalTime time, LocalDate date);
    Set<Appointment> findAllByExplainer(Explainer explainer);
    Optional<Appointment> findByDateAndExplainerAndStudentAndStart(LocalDate date, Explainer explainer, Student student, LocalTime time);
    Optional<Appointment> findByDateAndExplainerAndStart(LocalDate date, Explainer explainer, LocalTime time);

}
