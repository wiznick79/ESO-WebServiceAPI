package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerObject;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerService;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FilterExplainerServiceTest {
    @MockBean
    private FilterExplainerService filterExplainerService;
    @Test
    @VisibleForTesting
    void filter() {
        LocalTime t1 = LocalTime.of(10, 0);
        LocalTime t2 = LocalTime.of(12, 0);
        LocalDate d1 = LocalDate.now();
        Set<Explainer> explainers = new HashSet<>();
        Set<Language> languages = new HashSet<>();
        Set<Availability> availabilities = new HashSet<>();
        Degree degree = new Degree("Engenharia Informatica");
        Explainer explainer = new Explainer("Nikos Perris");
        explainers.add(explainer);
        Language language = new Language("Greek");
        languages.add(language);
        Availability availability = new Availability(explainer, t1, t2, d1);
        availabilities.add(availability);
        explainer.setAvailabilities(availabilities);
        explainer.setLanguages(languages);
        explainer.setDegree(degree);

        Map<String, String> map = new HashMap<>();
        map.put("name","Nikos Perris");
        map.put("day", "1998-01-12");
        map.put("start","10:00");
        map.put("end","12:00");
        map.put("degree", "Engenharia Informatica");
        map.put("language", "Greek");
        FilterExplainerObject filterExplainerObject= new FilterExplainerObject(map);
        filterExplainerObject.getExplainerName();
        filterExplainerObject.getDay();
        filterExplainerObject.getStart();
        filterExplainerObject.getEnd();
        filterExplainerObject.getDegree();
        filterExplainerObject.getLanguage();
        filterExplainerObject.getDay();
        assertNotNull( filterExplainerObject.getExplainerName());
        //filterExplainerService.filter(explainers,filterExplainerObject);



    }
}
