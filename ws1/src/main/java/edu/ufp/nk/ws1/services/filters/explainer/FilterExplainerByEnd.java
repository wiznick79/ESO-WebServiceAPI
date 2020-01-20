package edu.ufp.nk.ws1.services.filters.explainer;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.FilterI;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByEnd implements FilterI<Explainer> {

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime end;

    public FilterExplainerByEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public Set<Explainer> filter(Set<Explainer> data) {

        // No end filter
        if (this.end == null)
            return data;

        Set<Explainer> explainers = new HashSet<>();

        for (Explainer e : data) {
            for (Availability a : e.getAvailabilities()) {
                if (this.end.isBefore(a.getEnd()) || (this.end).equals(a.getEnd()))
                    explainers.add(e);
            }
        }

        return explainers;
    }
}