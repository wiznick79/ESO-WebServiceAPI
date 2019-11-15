package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentRepo extends CrudRepository<Appointment, Long> {
    Appointment findById (long id);
    Appointment findByDate (LocalDate date);
    Appointment findByStartAndDate (LocalTime time, LocalDate date);
}
