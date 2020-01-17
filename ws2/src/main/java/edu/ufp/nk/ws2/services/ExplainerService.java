package edu.ufp.nk.ws2.services;

import edu.ufp.nk.ws2.repositories.ExplainerRepo;
import edu.ufp.nk.ws2.repositories.UniversityRepo;
import edu.ufp.nk.ws2.models.Explainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExplainerService {
    @Autowired
    private UniversityRepo universityRepo;

    @Autowired
    private ExplainerRepo explainerRepo;

    public Iterable<Explainer> getAllExplainers(){
        return explainerRepo.findAll();
    }
}
