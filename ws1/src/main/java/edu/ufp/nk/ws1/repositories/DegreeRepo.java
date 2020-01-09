package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Degree;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DegreeRepo extends CrudRepository <Degree, Long> {
	Optional<Degree> findByName(String name);
	Optional<Degree> findById(long id);
}
