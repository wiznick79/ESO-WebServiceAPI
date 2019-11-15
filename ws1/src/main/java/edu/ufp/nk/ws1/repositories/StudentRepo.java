package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {
    Student findById(long id);
    Student findByName(String name);
    Student findByStudent_number(int number);
}
