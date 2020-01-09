package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Explainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExplainerRepo extends CrudRepository<Explainer, Long> {
    Optional<Explainer> findById (long id);
    Optional<Explainer> findByName (String name);
}
