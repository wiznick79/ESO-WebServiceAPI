package edu.ufp.nk.ws1.services.filters.explainer;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.FilterI;
import java.util.HashSet;
import java.util.Set;

public class FilterExplainerByName implements FilterI<Explainer> {

	private String explainerName;

	public FilterExplainerByName(String explainerName) {
		this.explainerName = explainerName;
	}

	@Override
	public Set<Explainer> filter(Set<Explainer> data){

		// No name filter
		if(this.explainerName == null)
			return data;

		Set<Explainer> explainers = new HashSet<>();
		for(Explainer e : data)
			if (e.getName().equalsIgnoreCase(this.explainerName))
				explainers.add(e);

		return explainers;
	}
}
