package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.College;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CollegeRepoTest {

    @Autowired
    private CollegeRepo collegeRepo;

    @Test
    public void test(){
        College college = new College("Humanas");
        College college1 =  new College("Ciencias");
        this.collegeRepo.save(college);
        assertEquals(1,collegeRepo.count());
        this.collegeRepo.save(college1);
        assertEquals(2,collegeRepo.count());
    }
}
