package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {
    Optional<Student> findById(long id);

    Optional<Student> findByName(String name);

    Optional<Student> findByStudentNumber(int studentNumber);
}
