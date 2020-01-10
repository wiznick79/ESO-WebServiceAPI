package edu.ufp.nk.ws2.repositories;

import edu.ufp.nk.ws2.models.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepo extends CrudRepository<University,Long> {
    Optional<University> findByName(String name);
}
