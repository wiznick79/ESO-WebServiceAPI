package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.repositories.AvailabilityRepo;
import edu.ufp.nk.ws1.repositories.DegreeRepo;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import edu.ufp.nk.ws1.repositories.LanguageRepo;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerService;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ExplainerService.class)
@VisibleForTesting
class ExplainerServiceTest {
    @Autowired
    private ExplainerService explainerService;
    @MockBean
    private ExplainerRepo explainerRepo;
    @MockBean
    private AvailabilityRepo availabilityRepo;
    @MockBean
    private DegreeRepo degreeRepo;
    @MockBean
    private LanguageRepo languageRepo;
    @MockBean
    private FilterExplainerService filterExplainerService;


    @Test
    @VisibleForTesting
    void accessExplainer(){
        Explainer explainer = new Explainer("Nikos Perris");
        Explainer explainer1 = new Explainer("Pedro Alves");
        this.explainerRepo.save(explainer);
        this.explainerRepo.save(explainer1);
        Degree degree = new Degree("Engenharia Informatica");
        degreeRepo.save(degree);
        Language language = new Language("English");
        languageRepo.save(language);
        LocalDate d1 = LocalDate.now();
        LocalTime t1 = LocalTime.now();
        LocalTime t2 = LocalTime.of(23, 0);
        Availability availability = new Availability(explainer, t1, t2, d1);
        availabilityRepo.save(availability);
        Set<Explainer> explainers = new HashSet<>();
        explainers.add(explainer1);
        explainers.add(explainer);
        when(this.explainerService.addLanguage(explainer,language.getName())).thenReturn(Optional.of(explainer));
        when(this.explainerService.findById(1L)).thenReturn(Optional.of(explainer));
        assertNotNull(explainer);
        when(this.explainerService.findAll()).thenReturn(explainers);
        when(this.explainerService.findByName(explainer.getName())).thenReturn(Optional.of(explainer));
        when(this.explainerService.createExplainer(explainer)).thenReturn(Optional.of(explainer));
        when(this.explainerService.createAvailability(availability)).thenReturn(Optional.of(explainer));
        when(this.explainerService.addDegree(explainer,degree.getName())).thenReturn(Optional.of(explainer));

    }

}
