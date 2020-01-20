package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends CrudRepository<Appointment, Long> {
    Optional<Appointment> findById(long id);

    Optional<Appointment> findByDate(LocalDate date);

    Optional<Appointment> findByStartAndDate(LocalTime time, LocalDate date);
}
