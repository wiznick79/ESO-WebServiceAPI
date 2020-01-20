package edu.ufp.nk.ws1.services.filters.explainer;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.AndFilter;
import edu.ufp.nk.ws1.services.filters.FilterI;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FilterExplainerService {
    public Set<Explainer> filter(Set<Explainer> explainers, FilterExplainerObject filterExplainerObject) {

        FilterI<Explainer> filterByName = new FilterExplainerByName(filterExplainerObject.getExplainerName());
        FilterI<Explainer> filterByDegree = new FilterExplainerByDegree(filterExplainerObject.getDegree());
        FilterI<Explainer> filterByLanguage = new FilterExplainerByLanguage(filterExplainerObject.getLanguage());
        FilterI<Explainer> filterByDay = new FilterExplainerByDay(filterExplainerObject.getDay());
        FilterI<Explainer> filterByStart = new FilterExplainerByStart(filterExplainerObject.getStart());
        FilterI<Explainer> filterByEnd = new FilterExplainerByEnd(filterExplainerObject.getEnd());

        FilterI<Explainer> filterByNameDegree = new AndFilter(filterByName, filterByDegree);
        FilterI<Explainer> filterByNameDegreeLanguage = new AndFilter(filterByNameDegree, filterByLanguage);

        FilterI<Explainer> filterByDayStart = new AndFilter(filterByDay, filterByStart);
        FilterI<Explainer> filterByDayStartEnd = new AndFilter(filterByDayStart, filterByEnd);

        return new AndFilter(filterByNameDegreeLanguage, filterByDayStartEnd).filter(explainers);
    }
}
