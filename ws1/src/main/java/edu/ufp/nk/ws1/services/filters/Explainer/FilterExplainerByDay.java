package edu.ufp.nk.ws1.services.filters.Explainer;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.FilterI;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByDay implements FilterI<Explainer> {

	private LocalDate day;

	public FilterExplainerByDay(LocalDate day) {
		this.day = day;
	}

	@Override
	public Set<Explainer> filter(Set<Explainer> data){

		// No day filter
		if(this.day == null)
			return data;

		Set<Explainer> explainers = new HashSet<>();
		DayOfWeek dayOfWeek = this.day.getDayOfWeek();

		for(Explainer e : data){
			for(Availability a : e.getAvailabilities()){
				if(a.getDayOfWeek() == dayOfWeek.getValue())
					explainers.add(e);
			}
		}

		return explainers;
	}
}
