package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ExplainerService {
    private ExplainerRepo explainerRepo;

    @Autowired
    public ExplainerService (ExplainerRepo explainerRepo){
        this.explainerRepo=explainerRepo;
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
        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(explainer.getName());
        if (optionalExplainer.isPresent()){
            return Optional.empty();
        }
        Explainer createdExplainer=this.explainerRepo.save(explainer);
        return Optional.of(createdExplainer);
    }

    public Optional<Explainer> findByName(String name) {return this.explainerRepo.findByName(name);}
}
