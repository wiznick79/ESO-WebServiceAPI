package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerByDay;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByDayTest {
    @Test
    @VisibleForTesting
    void filter(){
        LocalTime t1 = LocalTime.of(10,0);
        LocalTime t2 = LocalTime.of(12,0);
        LocalDate d1 = LocalDate.now();
        Set<Availability> availabilities = new HashSet<>();
        Set<Explainer> explainers = new HashSet<>();
        Explainer explainer = new Explainer("Nikos Perris");
        Availability availability = new Availability(explainer,t1,t2,d1);
        availabilities.add(availability);
        explainers.add(explainer);
        explainer.setAvailabilities(availabilities);


        FilterExplainerByDay filterExplainerByDay = new FilterExplainerByDay(LocalDate.now());
        assertEquals(1,filterExplainerByDay.filter(explainers).size());
        FilterExplainerByDay filterExplainerByDay1 = new FilterExplainerByDay(LocalDate.of(1999,1,12));
        assertEquals(0,filterExplainerByDay1.filter(explainers).size());
    }
}
