package edu.ufp.nk.ws2.repositories;

import edu.ufp.nk.ws2.university.Explainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExplainerRepo extends CrudRepository<Explainer, Long> {
    Optional<Explainer> findByName(String name);
}
