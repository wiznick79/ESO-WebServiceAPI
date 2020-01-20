package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerByLanguage;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterExplainerByLanguageTest {
    @Test
    @VisibleForTesting
    void filter() {
        Set<Explainer> explainers = new HashSet<>();
        Set<Language> languages = new HashSet<>();
        Set<Language> onlyEnglish = new HashSet<>();
        Explainer explainer = new Explainer("Nikos Perris");
        Explainer explainer1 = new Explainer("Pedro Alves");
        Language language1 = new Language("Greek");
        Language language2 = new Language("English");
        languages.add(language1);
        languages.add(language2);
        onlyEnglish.add(language2);
        explainers.add(explainer);
        explainers.add(explainer1);
        explainer.setLanguages(languages);
        explainer1.setLanguages(onlyEnglish);


        FilterExplainerByLanguage filterExplainerByLanguage = new FilterExplainerByLanguage("Greek");
        assertEquals(1, filterExplainerByLanguage.filter(explainers).size());
        FilterExplainerByLanguage filterExplainerByLanguage1 = new FilterExplainerByLanguage("English");
        assertEquals(2, filterExplainerByLanguage1.filter(explainers).size());
        FilterExplainerByLanguage filterExplainerByLanguage2 = new FilterExplainerByLanguage("Portuguese");
        assertEquals(0, filterExplainerByLanguage2.filter(explainers).size());
    }
}
