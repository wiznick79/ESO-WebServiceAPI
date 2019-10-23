package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Degree;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeRepo extends CrudRepository <Degree, Long> {
	Degree findByName(String name);
	Degree findById(long id);
}
