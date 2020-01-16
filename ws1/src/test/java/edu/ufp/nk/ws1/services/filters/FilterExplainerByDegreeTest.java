package edu.ufp.nk.ws1.services.filters;

import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.filters.explainer.FilterExplainerByDegree;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.Set;

class FilterExplainerByDegreeTest {
    @Test
    @VisibleForTesting
    void filter(){
        Set<Explainer> explainers = new HashSet<>();
        Explainer explainer = new Explainer("Nikos Perris");
        Degree degree1 =new Degree("Enfermagem");
        Degree degree = new Degree("Engenharia Informatica");
        explainer.setDegree(degree);
        explainers.add(explainer);


        FilterExplainerByDegree filterExplainerByDegree = new FilterExplainerByDegree("Engenharia Informatica");
        assertEquals(1,filterExplainerByDegree.filter(explainers).size());
        FilterExplainerByDegree filterExplainerNoDegree=new FilterExplainerByDegree("Enfermagem");
        assertEquals(0,filterExplainerNoDegree.filter(explainers).size());
        Explainer explainer1 =new Explainer("Enfermeiro");
        explainer1.setDegree(degree1);
        explainers.add(explainer1);
        FilterExplainerByDegree filterExplainer=new FilterExplainerByDegree("Enfermagem");
        assertEquals(1,filterExplainerNoDegree.filter(explainers).size());
    }
}
