package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.repositories.AvailabilityRepo;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ExplainerService {
    private ExplainerRepo explainerRepo;
    private AvailabilityRepo availabilityRepo;

    @Autowired
    public ExplainerService (ExplainerRepo explainerRepo, AvailabilityRepo availabilityRepo){
        this.explainerRepo=explainerRepo;
        this.availabilityRepo = availabilityRepo;
    }

    public Set<Explainer> findAll(){
        Set<Explainer> explainers = new HashSet<>();
        for (Explainer explainer:this.explainerRepo.findAll()){
            explainers.add(explainer);
        }
        return explainers;
    }

    public Optional<Explainer> findById(Long id) {
        return this.explainerRepo.findById(id);
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

    public Optional<Explainer> addAvailabilitie(Long id, Availability availability){
        Optional<Explainer> optionalExplainer = this.explainerRepo.findById(id);

        if(optionalExplainer.isEmpty())
            return Optional.empty();

        // TODO: MAKE ALL THE TESTS
        availability.setExplainer(optionalExplainer.get());
        optionalExplainer.get().getAvailabilities().add(availability);
        this.availabilityRepo.save(availability);
        this.explainerRepo.save(optionalExplainer.get());

        return optionalExplainer;
    }






    public Optional<Explainer> findByName(String name) {return this.explainerRepo.findByName(name);}
}
