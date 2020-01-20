package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.repositories.AvailabilityRepo;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest (classes = AvailabilityService.class)
@VisibleForTesting
class AvailabilityServiceTest {
    @Autowired
    private AvailabilityService availabilityService;
    @MockBean
    private AvailabilityRepo availabilityRepo;

    @Test
    @VisibleForTesting
    void accessAvailability(){
        Explainer explainer = new Explainer("Nikos Perris");
        LocalDate d1 = LocalDate.now();
        LocalTime t1 = LocalTime.now();
        LocalTime t2 = LocalTime.of(23, 0);
        Availability availability = new Availability(explainer, t1, t2, d1);
        availability.setId(1L);
        Availability availability2 = new Availability(explainer, t1, t2, d1);
        this.availabilityRepo.save(availability);
        this.availabilityRepo.save(availability2);
        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(availability);
        availabilities.add(availability2);

        when(this.availabilityService.findAll()).thenReturn(availabilities);
        assertNotNull(availabilities);

        when(this.availabilityService.findById(1L)).thenReturn(Optional.of(availability));
        assertNotNull(availability);

        when(this.availabilityService.findByDayOfWeekAndStart(d1,t1)).thenReturn(Optional.of(availability));
        assertNotNull(availability);

        this.availabilityService.createAvailability(availability);

    }
}
