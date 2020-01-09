package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Availability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AvailabilityRepo extends CrudRepository<Availability, Long> {
    Optional<Availability> findById (long id);
    Optional<Availability> findByDayOfWeekAndStart (int day, LocalTime start);

}
