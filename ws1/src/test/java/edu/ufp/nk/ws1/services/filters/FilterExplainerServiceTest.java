package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Language;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerServiceTest {
    @Test
    @VisibleForTesting
    void filter(){
        LocalTime t1 = LocalTime.of(10,0);
        LocalTime t2 = LocalTime.of(12,0);
        LocalDate d1 = LocalDate.now();
        Set<Explainer> explainers = new HashSet<>();
        Set<Language> languages = new HashSet<>();
        Set<Availability> availabilities = new HashSet<>();
        Explainer explainer = new Explainer("Nikos Perris");
        explainers.add(explainer);
        Language language = new Language("Greek");
        languages.add(language);
        Availability availability = new Availability(explainer,t1,t2,d1);
        availabilities.add(availability);
        explainer.setAvailabilities(availabilities);
        explainer.setLanguages(languages);


        //FilterExplainerObject filterExplainerObject= new FilterExplainerObject();



    }
}
