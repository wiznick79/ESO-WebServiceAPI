package edu.ufp.nk.ws1.services.filters.Explainer;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.FilterI;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByEnd implements FilterI<Explainer> {

	private LocalTime end;

	public FilterExplainerByEnd(LocalTime end) {
		this.end = end;
	}

	@Override
	public Set<Explainer> filter(Set<Explainer> data){

		// No end filter
		if(this.end == null)
			return data;

		Set<Explainer> explainers = new HashSet<>();

		for(Explainer e : data){
			for(Availability a : e.getAvailabilities()){
				if(a.getStart().isAfter(this.end) || (a.getStart().equals(end)))
					explainers.add(e);
			}
		}

		return explainers;
	}
}
