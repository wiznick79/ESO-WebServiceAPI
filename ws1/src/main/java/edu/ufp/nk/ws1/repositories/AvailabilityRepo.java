package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Availability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface AvailabilityRepo extends CrudRepository<Availability, Long> {
    Availability findById (long id);
    Availability findByDay_of_weekAndStart(int day, LocalTime start);

}
