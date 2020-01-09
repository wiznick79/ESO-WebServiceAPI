package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.repositories.AvailabilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AvailabilityService {
    private AvailabilityRepo availabilityRepo;

    @Autowired
    public AvailabilityService(AvailabilityRepo availabilityRepo) {
        this.availabilityRepo=availabilityRepo;

    }

    public Set<Availability> findAll() {
        Set<Availability> availabilities = new HashSet<>();
        for (Availability availability:this.availabilityRepo.findAll()){
            availabilities.add(availability);
        }
        return availabilities;
    }

    public Optional<Availability> findById(Long id) {
        return this.availabilityRepo.findById(id);
    }

    public Optional<Availability> createAvailability(Availability availability) {
        Optional<Availability> optionalAvailability = this.availabilityRepo.findByDayOfWeekAndStart(availability.getDayOfWeek(), availability.getStart());
        if (optionalAvailability.isPresent()){
            return Optional.empty();
        }
        Availability createAvailability = this.availabilityRepo.save(availability);
        return Optional.of(createAvailability);
    }

    public Optional<Availability> findByDayOfWeekAndStart (int day, LocalTime start) {
        return this.availabilityRepo.findByDayOfWeekAndStart(day, start);
    }
}
