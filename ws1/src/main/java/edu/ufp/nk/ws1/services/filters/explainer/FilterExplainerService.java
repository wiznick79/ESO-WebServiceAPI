package edu.ufp.nk.ws1.services.filters.explainer;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.AndFilter;
import edu.ufp.nk.ws1.services.filters.FilterI;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class FilterExplainerService {
	public Set<Explainer> filter(Set<Explainer> explainers , FilterExplainerObject filterExplainerObject){

		FilterI<Explainer> FilterByName = new FilterExplainerByName(filterExplainerObject.getExplainerName());
		FilterI<Explainer> FilterByDegree = new FilterExplainerByDegree(filterExplainerObject.getDegree());
		FilterI<Explainer> FilterByDay = new FilterExplainerByDay(filterExplainerObject.getDay());
		FilterI<Explainer> FilterByStart = new FilterExplainerByStart(filterExplainerObject.getStart());
		FilterI<Explainer> FilterByEnd = new FilterExplainerByEnd(filterExplainerObject.getEnd());

		FilterI<Explainer> FilterByNameDegree = new AndFilter(FilterByName, FilterByDegree);
		FilterI<Explainer> FilterByDayStart = new AndFilter(FilterByDay,FilterByStart);
		FilterI<Explainer> FilterByDayStartEnd = new AndFilter(FilterByDayStart,FilterByEnd);

		return new AndFilter(FilterByNameDegree, FilterByDayStartEnd).filter(explainers);
	}
}
