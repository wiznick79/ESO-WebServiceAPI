package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerByEnd;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

class FilterExplainerByEndTest {
    @Test
    @VisibleForTesting
    void filter(){
        LocalTime t1 = LocalTime.of(10,0);
        LocalTime t2 = LocalTime.of(12,0);
        LocalDate d1 = LocalDate.now();
        Set<Availability> availabilities = new HashSet<>();
        Set<Explainer> explainers = new HashSet<>();
        Explainer explainer = new Explainer("Nikos Perris");
        explainers.add(explainer);
        Availability availability = new Availability(explainer,t1,t2,d1);
        availabilities.add(availability);
        explainer.setAvailabilities(availabilities);

        FilterExplainerByEnd filterExplainerByEnd = new FilterExplainerByEnd(LocalTime.of(12,0));
        assertEquals(1,filterExplainerByEnd.filter(explainers).size());
        FilterExplainerByEnd filterExplainerByEnd1 = new FilterExplainerByEnd(LocalTime.of(20,0));
        assertEquals(0, filterExplainerByEnd1.filter(explainers).size());
    }
}
