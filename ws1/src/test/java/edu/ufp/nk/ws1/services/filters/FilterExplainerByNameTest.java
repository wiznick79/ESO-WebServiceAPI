package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerByName;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterExplainerByNameTest {
    @Test
    @VisibleForTesting
    void filter(){
        Set<Explainer> explainers = new HashSet<>();
        Explainer explainer = new Explainer("Nikos Perris");
        explainers.add(explainer);

        FilterExplainerByName filterExplainerByName = new FilterExplainerByName(explainer.getName());
        assertEquals(1,filterExplainerByName.filter(explainers).size());
        FilterExplainerByName filterNonExistentExplainer = new FilterExplainerByName("Pedro Alves");
        assertEquals(0,filterNonExistentExplainer.filter(explainers).size());
    }
}
