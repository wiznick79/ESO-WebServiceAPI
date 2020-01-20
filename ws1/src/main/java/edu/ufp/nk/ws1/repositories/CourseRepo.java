package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepo extends CrudRepository<Course, Long> {
    Optional<Course> findByName(String name);

    Optional<Course> findById(long id);
}
