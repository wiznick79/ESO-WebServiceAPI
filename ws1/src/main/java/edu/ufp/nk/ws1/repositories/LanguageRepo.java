package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepo extends CrudRepository<Language, Long> {
    Optional<Language> findById (long id);
    Optional<Language> findByName (String name);
}
