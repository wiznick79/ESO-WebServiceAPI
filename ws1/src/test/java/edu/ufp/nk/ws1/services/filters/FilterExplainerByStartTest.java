package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerByStart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

class FilterExplainerByStartTest {
    @Test
    void filter(){
        LocalTime t1 = LocalTime.of(10,0);
        LocalTime t2 = LocalTime.of(12,0);
        LocalDate d1 = LocalDate.now();
        Set<Explainer> explainers = new HashSet<>();
        Set<Availability> availabilities = new HashSet<>();
        Explainer explainer = new Explainer("Nikos Perris");
        Availability availability = new Availability(explainer,t1,t2,d1);
        explainers.add(explainer);
        availabilities.add(availability);
        explainer.setAvailabilities(availabilities);

        FilterExplainerByStart filterExplainerByStart = new FilterExplainerByStart(LocalTime.of(10,0));
        assertEquals(1,filterExplainerByStart.filter(explainers).size());
    }
}
