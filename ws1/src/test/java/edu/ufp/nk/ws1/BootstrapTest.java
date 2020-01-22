package edu.ufp.nk.ws1;

import edu.ufp.nk.ws1.repositories.CollegeRepo;
import edu.ufp.nk.ws1.repositories.CourseRepo;
import edu.ufp.nk.ws1.repositories.DegreeRepo;
import edu.ufp.nk.ws1.repositories.StudentRepo;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Bootstrap.class)
@VisibleForTesting
public class BootstrapTest {

    @Autowired
    private Bootstrap bootstrap;

    @MockBean
    private DegreeRepo degreeRepo;
    @MockBean
    private CourseRepo courseRepo;
    @MockBean
    private StudentRepo studentRepo;
    @MockBean
    private CollegeRepo collegeRepo;

    @Test
    @VisibleForTesting
    void bootstrap(){
        Bootstrap bootstrap = new Bootstrap(degreeRepo,courseRepo,studentRepo,collegeRepo);
        assertNotNull(bootstrap);
    }
}
