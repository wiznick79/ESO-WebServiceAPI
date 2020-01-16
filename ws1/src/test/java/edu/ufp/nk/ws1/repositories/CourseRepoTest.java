package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Course;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CourseRepoTest {
    @Autowired
    private CourseRepo courseRepo;


    @Test
    @VisibleForTesting
    void test(){
        Course course = new Course("Multimedia");
        Course course1 = new Course("Software Engineer");
        this.courseRepo.save(course);
        assertEquals(1,courseRepo.count());
        this.courseRepo.save(course1);
        assertEquals(2,courseRepo.count());
    }
}
