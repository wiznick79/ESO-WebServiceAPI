package edu.ufp.nk.ws1.services.filters.Explainer;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.FilterI;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByStart implements FilterI<Explainer> {

	private LocalTime start;

	public FilterExplainerByStart(LocalTime start) {
		this.start = start;
	}

	@Override
	public Set<Explainer> filter(Set<Explainer> data){

		// No start filter
		if(this.start == null)
			return data;

		Set<Explainer> explainers = new HashSet<>();

		for(Explainer e : data){
			for(Availability a : e.getAvailabilities()){
				if(a.getStart().isBefore(this.start) || (a.getStart().equals(start)))
					explainers.add(e);
			}
		}

		return explainers;
	}
}
