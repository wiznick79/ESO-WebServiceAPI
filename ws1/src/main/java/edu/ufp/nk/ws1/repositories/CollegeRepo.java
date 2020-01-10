package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.College;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollegeRepo extends CrudRepository<College,Long> {
    Optional<College> findByName(String name);

}
