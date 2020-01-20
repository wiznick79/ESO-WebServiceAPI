package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Student;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class StudentRepoTest {

    @Autowired
    private StudentRepo studentRepo;

    @Test
    @VisibleForTesting
    void test() {
        Student student = new Student("student", 3700);
        this.studentRepo.save(student);
        System.out.println(student.getId());
        assertEquals(1, this.studentRepo.count());
        Student student1 = new Student("student1", 30000);
        this.studentRepo.save(student1);
        System.out.println(student1.getId());
        assertEquals(2, studentRepo.count());

    }
}
