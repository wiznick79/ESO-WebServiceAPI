package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends CrudRepository <Course, Long> {
	Course findByName(String name);
	Course findById(long id);
}
