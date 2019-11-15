package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends CrudRepository<Language, Long> {
    Language findById (long id);
    Language findByName (String name);
}
