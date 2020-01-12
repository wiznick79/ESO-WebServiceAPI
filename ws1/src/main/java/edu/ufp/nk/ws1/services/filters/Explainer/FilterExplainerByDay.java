package edu.ufp.nk.ws1.services.filters.Explainer;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.FilterI;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByDay implements FilterI<Explainer> {

	@DateTimeFormat(pattern = "dd-MM-yyyy")
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

		for(Explainer e : data){
			for(Availability a : e.getAvailabilities()){
				if(a.getDay().equals(day))
					explainers.add(e);
			}
		}

		return explainers;
	}
}
