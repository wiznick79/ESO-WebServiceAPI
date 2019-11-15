package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Explainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExplainerRepo extends CrudRepository<Explainer, Long> {
    Explainer findById (long id);
    Explainer findByName (String id);
}
