package edu.ufp.nk.ws1.repositories;


import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.LocalTime;

@DataJpaTest
public class AvailabilityRepoTest {

    @Autowired
    private AvailabilityRepo availabilityRepo;
    @Autowired
    private ExplainerRepo explainerRepo;


    @Test
    @VisibleForTesting
    void test(){
        Explainer explainer = new Explainer("Nikos Perris");
        this.explainerRepo.save(explainer);
        LocalDate d1 = LocalDate.now();
        LocalDate d2 = LocalDate.of(2020,1,1);
        LocalTime t1 = LocalTime.of(14,50);
        LocalTime t2 = LocalTime.of(20,50);
        Availability availability = new Availability(explainer,t1,t2,d1);
        this.availabilityRepo.save(availability);
        assertEquals(1,availabilityRepo.count());
        Availability availability1 = new Availability(explainer,t1,t2,d2);
        this.availabilityRepo.save(availability1);
        assertEquals(2,availabilityRepo.count());
    }
}
