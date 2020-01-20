package edu.ufp.nk.ws1.services.filters.explainer;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.filters.FilterI;

import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByLanguage implements FilterI<Explainer> {

    private String language;

    public FilterExplainerByLanguage(String language) {
        this.language = language;
    }

    @Override
    public Set<Explainer> filter(Set<Explainer> data) {

        // No degree filter
        if (this.language == null)
            return data;

        Set<Explainer> explainers = new HashSet<>();

        for (Explainer e : data) {
            for (Language lang : e.getLanguages()) {
                if (lang.getName().equalsIgnoreCase(language)) {
                    explainers.add(e);
                }
            }
        }

        return explainers;
    }
}