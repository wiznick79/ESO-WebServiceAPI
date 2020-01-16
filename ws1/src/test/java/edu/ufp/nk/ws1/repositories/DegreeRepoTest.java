package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Degree;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class DegreeRepoTest {
    @Autowired
    private DegreeRepo degreeRepo;


    @Test
    @VisibleForTesting
    void test(){
        Degree degree = new Degree("Engenharia Informatica");
        Degree degree1 = new Degree("Enfermagem");
        this.degreeRepo.save(degree);
        assertEquals(1,degreeRepo.count());
        this.degreeRepo.save(degree1);
        assertEquals(2,degreeRepo.count());

    }

}
