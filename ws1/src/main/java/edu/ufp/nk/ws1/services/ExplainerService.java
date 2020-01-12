package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.repositories.AvailabilityRepo;
import edu.ufp.nk.ws1.repositories.DegreeRepo;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import edu.ufp.nk.ws1.services.filters.Explainer.FilterExplainerObject;
import edu.ufp.nk.ws1.services.filters.Explainer.FilterExplainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ExplainerService {
    private ExplainerRepo explainerRepo;
    private AvailabilityRepo availabilityRepo;
    private DegreeRepo degreeRepo;
    private FilterExplainerService filterExplainerService;

    @Autowired
    public ExplainerService (ExplainerRepo explainerRepo, AvailabilityRepo availabilityRepo, DegreeRepo degreeRepo, FilterExplainerService filterExplainerService) {
        this.explainerRepo=explainerRepo;
        this.availabilityRepo = availabilityRepo;
        this.degreeRepo = degreeRepo;
        this.filterExplainerService = filterExplainerService;
    }

    public Set<Explainer> findAll(){
        Set<Explainer> explainers = new HashSet<>();
        for (Explainer explainer:this.explainerRepo.findAll()){
            explainers.add(explainer);
        }
        return explainers;
    }

    public Optional<Explainer> createExplainer(Explainer explainer){

        //TODO: EXPLICADORES PODEM TER MESMO NOME TIRAR CONDIÇAO?
        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(explainer.getName());
        if (optionalExplainer.isPresent()){
            return Optional.empty();
        }
        Explainer createdExplainer=this.explainerRepo.save(explainer);
        return Optional.of(createdExplainer);
    }

    public Optional<Explainer> createAvailability(Availability availability){
        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(availability.getExplainer().getName());
        // TODO: MAKE ALL THE TESTS
        if (optionalExplainer.isEmpty()
                || availability.getStart().isAfter(availability.getEnd())
                || availability.getStart().equals(availability.getEnd())
                || availability.getDayOfWeek()<1 || availability.getDayOfWeek()>7
                || Duration.between(availability.getStart(),availability.getEnd()).toHours()<1 )
            return Optional.empty();

        availability.setExplainer(optionalExplainer.get());
        optionalExplainer.get().getAvailabilities().add(availability);
        this.availabilityRepo.save(availability);
        this.explainerRepo.save(optionalExplainer.get());

        return optionalExplainer;
    }

    public Optional<Explainer> addDegree(Explainer explainer, String degreeName) {
        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(explainer.getName());
        Optional<Degree> optionalDegree = this.degreeRepo.findByName(degreeName);

        if (optionalExplainer.isEmpty() || optionalDegree.isEmpty())
            return Optional.empty();

        optionalExplainer.get().setDegree(optionalDegree.get());
        this.explainerRepo.save(optionalExplainer.get());

        return optionalExplainer;
    }

    public Set<Explainer> filterExplainers(Map<String, String> query){
        FilterExplainerObject filterExplainerObject = new FilterExplainerObject(query);
        Set<Explainer> explainers = this.findAll();

        return this.filterExplainerService.filter(explainers, filterExplainerObject);
    }


    public Optional<Explainer> findByName(String name) {
        return this.explainerRepo.findByName(name);
    }

    public Optional<Explainer> findById(Long id) {
        return this.explainerRepo.findById(id);
    }
}
