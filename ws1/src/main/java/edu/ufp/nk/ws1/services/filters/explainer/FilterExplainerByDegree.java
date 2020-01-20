package edu.ufp.nk.ws1.services.filters.explainer;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.FilterI;

import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByDegree implements FilterI<Explainer> {

    private String degree;

    public FilterExplainerByDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public Set<Explainer> filter(Set<Explainer> data) {

        // No degree filter
        if (this.degree == null)
            return data;

        // TODO: Fix this. Missing Explainer Courses? Degrees?
        Set<Explainer> explainers = new HashSet<>();
        for (Explainer e : data) {
            if (e.getDegree().getName().equalsIgnoreCase(this.degree)) {
                explainers.add(e);
            }
        }

        return explainers;
    }
}
